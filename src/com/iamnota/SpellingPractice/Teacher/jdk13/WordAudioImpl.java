/*
 * WordAudioImpl.java
 *
 * Copyright (C) 2002-2004 John S. Jacob <jsjacob@iamnota.com>
 *
 * This file is part of SpellingPractice.
 *
 * SpellingPractice is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SpellingPractice is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SpellingPractice; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package com.iamnota.SpellingPractice.Teacher.jdk13;

import com.iamnota.SpellingPractice.Teacher.WordController;
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.WordAudio;
import com.iamnota.SpellingPractice.Teacher.LogView;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.LineUnavailableException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class WordAudioImpl
    implements WordAudio
{
    private WordController wordController;
    private WordModel wordModel;

    private WordPlay wordPlay;
    private WordRecord wordRecord;

    private final int lineBufferLength = 16384;

    public WordAudioImpl(WordController wordController, WordModel wordModel)
    {
	this.wordController = wordController;
	this.wordModel = wordModel;

	this.wordPlay = null;
	this.wordRecord = null;
    }

    public void playStartWord()
    {
	if (this.wordPlay != null)
	{
	    this.wordPlay.stop();
	}
	this.wordPlay = new WordPlay(this.wordController, this.wordModel);
	this.wordPlay.start();
    }

    public void playStopWord()
    {
	if (this.wordPlay != null)
	{
	    this.wordPlay.stop();
	}
    }

    public void recordStartWord()
    {
	if (this.wordRecord != null)
	{
	    this.wordRecord.stop();
	}
	this.wordRecord = new WordRecord(this.wordController, this.wordModel);
	this.wordRecord.start();
    }

    public void recordStopWord()
    {
	if (this.wordRecord != null)
	{
	    this.wordRecord.stop();
	}
    }


    /**
     * PCM-encoding, 8kHz sampling rate, 16-bit sample,
     * 1 channel, 2-byte frame size, 8kHz frame rate, big-endian
     */
    protected static AudioFormat getFormat()
    {
	return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000, 16, 1, 2, 8000, true);
    }


    /**
     * Write data to the OutputChannel.
     */
    public class Play
	implements Runnable
    {
	private AudioInputStream audioInputStream;
	private LogView logView;
	private Thread thread;

	private boolean isStopRequested = false;

	private Play() {}

	public Play(AudioInputStream audioInputStream, LogView logView)
	{
	    this.audioInputStream = audioInputStream;
	    this.logView = logView;
	    this.thread = new Thread(this);
	}

	public void start()
	{
	    this.thread.start();
	}

        public void stop()
	{
	    this.isStopRequested = true;
        }
        
        public void run()
	{
            // Check for something to play

            if (this.audioInputStream == null)
	    {
                this.logView.debug("Play: No loaded audio to play back");
                return;
            }

            // Reset stream

            try
	    {
                this.audioInputStream.reset();
            }

	    catch (Exception e)
	    {
                this.logView.debug("Play: Unable to reset the stream");
		e.printStackTrace(this.logView.getDebugStream());
		this.logView.warning("Couldn't reset audio stream for playing: " + e.getMessage());
                return;
            }

            // Create AudioInputStream

            AudioFormat format = getFormat();
            AudioInputStream playInputStream = AudioSystem.getAudioInputStream(format, audioInputStream);
                        
            if (playInputStream == null)
	    {
                this.logView.debug("Play: Unable to convert stream of format " + audioInputStream + " to format " + format);
                this.logView.error("Unable to convert audio stream of format " + audioInputStream + " to format " + format);
                return;
            }

	    // Open line

	    SourceDataLine line;

            try
	    {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		if (!AudioSystem.isLineSupported(info))
		{
		    this.logView.debug("Play: Line matching " + info + " not supported.");
		    this.logView.error("Audio line matching " + info + " not supported.");
		    return;
		}

                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format, lineBufferLength);
            }

	    catch (LineUnavailableException e)
	    {
                this.logView.debug("Play: Unable to open the line: ");
		e.printStackTrace(this.logView.getDebugStream());
                this.logView.error("Unable to open audio line: " + e.getMessage());
                return;
            }

            // Play audio

            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];

            line.start();

	    boolean isDone = false;
            while (!this.isStopRequested && !isDone)
	    {
                try
		{
		    int bytesRead = 0;
                    if ((bytesRead = playInputStream.read(data)) == -1)
		    {
                        isDone = true;
                    }
		    else
		    {
			int bytesLeft = bytesRead;
			while (bytesLeft > 0 )
			{
			    bytesLeft -= line.write(data, (bytesRead-bytesLeft), bytesLeft);
			}
		    }
                }

		catch (Exception e)
		{
                    this.logView.debug("Play: Error during play:");
		    e.printStackTrace(this.logView.getDebugStream());
                    break;
                }
            }

	    // Play rest of stream if stop wasn't requested
            if (!this.isStopRequested)
	    {
                line.drain();
            }

            line.stop();
            line.close();
        }
    }


    public class WordPlay
	extends Play
    {
	private WordController wordController;

	public WordPlay(WordController wordController, WordModel wordModel)
	{
	    super(wordModel.getWordAudio(), wordController.getLogView());

	    this.wordController = wordController;
	}

	public void run()
	{
	    this.wordController.playStartedWord();

	    super.run();

	    this.wordController.playStoppedWord();
	}
    }
    

    /** 
     * Reads data from the input channel and writes to the output stream
     */
    public class Record
	implements Runnable
    {
	private AudioInputStream audioInputStream;
	private LogView logView;
	private int maxDurationInMillisecs;
	private Thread thread;
	
	private boolean isStopRequested = false;

	public Record(LogView logView, int maxDurationInSeconds)
	{
	    this.audioInputStream = null;
	    this.logView = logView;
	    this.maxDurationInMillisecs = (maxDurationInSeconds * 1000);
	    this.thread = new Thread(this);
	}

	public AudioInputStream getAudioInputStream()
	{
	    return this.audioInputStream;
	}

	public void start()
	{
	    this.thread.start();
	}

        public void stop()
	{
	    this.isStopRequested = true;
        }
        
        public void run()
	{
	    this.logView.debug("Record.run(): Begin");

	    AudioFormat format = getFormat();

	    // Open line

	    TargetDataLine line;

            try
	    {
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info))
		{
		    this.logView.debug("Record: Line matching " + info + " not supported.");
		    this.logView.error("Audio line matching " + info + " not supported.");
		    return;
		}

                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format, line.getBufferSize());
            }

	    catch (LineUnavailableException e)
	    {
                this.logView.debug("Record: Unable to open the line: ");
		e.printStackTrace(this.logView.getDebugStream());
		this.logView.error("Unable to open audio line: " + e.getMessage());
                return;
            }

	    catch (Exception e)
	    {
		e.printStackTrace(this.logView.getDebugStream());
		this.logView.error(e.getMessage());
                return;
            }

            // Save audio to internal buffer
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];
            
            line.start();

	    long startTime = new Date().getTime();

	    boolean isDone = false;
            while (!this.isStopRequested && !isDone)
	    {
		int bytesRead = 0;
                if((bytesRead = line.read(data, 0, bufferLengthInBytes)) == -1)
		{
		    isDone = true;
                }
		else
		{
		    out.write(data, 0, bytesRead);
		}

		boolean isOverMaxDuration = ((new Date().getTime() - startTime) > this.maxDurationInMillisecs);
		if (isOverMaxDuration)
		{
		    isDone = true;
		}
            }

            line.stop();
            line.close();

            // stop and close the output stream

            try
	    {
                out.flush();
                out.close();
            }

	    catch (IOException e)
	    {
                e.printStackTrace(this.logView.getDebugStream());
		this.logView.error(e.getMessage());
            }

            // load bytes into audioInputStream

            byte audioBytes[] = out.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
            this.audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);

            try
	    {
                audioInputStream.reset();
            }

	    catch (Exception e)
	    {
                e.printStackTrace(this.logView.getDebugStream()); 
		this.logView.error(e.getMessage());
                return;
            }

	    this.logView.debug("Record.run(): End");
        }
    }


    public class WordRecord
	extends Record
    {
	private WordController wordController;
	private WordModel wordModel;

	public WordRecord(WordController wordController, WordModel wordModel)
	{
	    super(wordController.getLogView(), wordController.getPreferenceController().getWordAudioMaxDurationInSeconds());

	    this.wordController = wordController;
	    this.wordModel = wordModel;
	}

	public void run()
	{
	    this.wordController.recordStartedWord();

	    super.run();

	    try
	    {
		this.wordModel.setWordAudio(getAudioInputStream());
	    }

	    catch (IOException e)
	    {
		e.printStackTrace(this.wordController.getLogView().getDebugStream());
		this.wordController.getLogView().error(e.getMessage());
	    }

	    this.wordController.recordStoppedWord();
	}
    }
}
