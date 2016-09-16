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

package com.iamnota.SpellingPractice.Student.jdk13;

import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.WordModel;
import com.iamnota.SpellingPractice.Student.WordAudio;
import com.iamnota.SpellingPractice.Student.PreferenceModel;
import com.iamnota.SpellingPractice.Student.LogView;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;

public class WordAudioImpl
    implements WordAudio
{
    private Controller controller;
    private WordModel wordModel;

    private WordPlay wordPlay;

    private final int lineBufferLength = 16384;

    public WordAudioImpl(Controller controller, WordModel wordModel)
    {
	this.controller = controller;
	this.wordModel = wordModel;

	this.wordPlay = null;
    }

    public void playStartWord()
    {
	if (this.wordPlay != null)
	{
	    this.wordPlay.stop();
	}
	this.wordPlay = new WordPlay(this.controller, this.wordModel);
	this.wordPlay.start();
    }

    public void playStopWord()
    {
	if (this.wordPlay != null)
	{
	    this.wordPlay.stop();
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

	protected void setAudioInputStream(AudioInputStream audioInputStream)
	{
	    this.audioInputStream = audioInputStream;
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
		this.logView.error("Unable to reset audio stream: " + e.getMessage());
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
		    this.logView.error("Error during play: " + e.getMessage());
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
	private Controller controller;

	public WordPlay(Controller controller, WordModel wordModel)
	{
	    super(wordModel.getWordAudio(), controller.getLogView());

	    this.controller = controller;
	}

	public void run()
	{
	    this.controller.listenStarted();

	    super.run();

	    stop();

	    this.controller.listenStopped();
	}
    }
}
