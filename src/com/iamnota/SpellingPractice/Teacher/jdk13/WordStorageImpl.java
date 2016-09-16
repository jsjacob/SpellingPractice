/*
 * WordStorageImpl.java
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

import com.iamnota.SpellingPractice.Teacher.WordStorage;
import com.iamnota.SpellingPractice.Teacher.WordController;
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.Map;
import java.util.HashMap;

public class WordStorageImpl
    implements WordStorage
{
    private WordController wordController;
    private WordModel wordModel;

    private static final String WORD_TEXT = "word.txt";
    private static final String WORD_AUDIO = "word.wav";


    public WordStorageImpl(WordController wordController, WordModel wordModel)
    {
	this.wordController = wordController;
	this.wordModel = wordModel;
    }

    public Map getIdsToWords()
    {
	HashMap map = new HashMap();

	PreferenceController preference = this.wordController.getPreferenceController();
	File directory = new File(preference.getWordStorageDirectory());

	String[] ids =  directory.list();
	if (ids != null)
	{
	    for (int i = 0; i < ids.length; i++)
	    {
		String id = ids[i];
		
		try
		{
		    File wordDirectory = new File(directory.getPath() + File.separator + id);
		    FileReader wordTextFr = new FileReader(wordDirectory.getPath() + File.separator + WORD_TEXT);
		    StringBuffer wordTextSb = new StringBuffer();
		    int bufferLength = 256;
		    char[] buffer = new char[bufferLength];
		    for (int charsRead = wordTextFr.read(buffer); charsRead != -1; charsRead = wordTextFr.read(buffer))
		    {
			wordTextSb.append(buffer, 0, charsRead);
		    }
		    
		    map.put(id, wordTextSb.toString());
		}
		
		catch (IOException e)
		{
		    // Ignore
		    ;
		}
	    }
	}

	return map;
    }

    public void load(String id)
	throws IOException
    {
	this.wordController.getLogView().debug("WordStorageImpl.load()");

	String wordText = null;
	AudioInputStream wordAudio = null;

	PreferenceController preference = this.wordController.getPreferenceController();
	File wordDirectory = new File(preference.getWordStorageDirectory() + File.separator + id);
	FileReader wordTextFr = new FileReader(wordDirectory.getPath() + File.separator + WORD_TEXT);
	StringBuffer wordTextSb = new StringBuffer();
	int bufferLength = 256;
	char[] buffer = new char[bufferLength];
	for (int charsRead = wordTextFr.read(buffer); charsRead != -1; charsRead = wordTextFr.read(buffer))
	{
	    wordTextSb.append(buffer, 0, charsRead);
	}
	wordText = wordTextSb.toString();

	try
	{
	    File wordAudioFile = new File(wordDirectory.getPath() + File.separator + WORD_AUDIO);
	    wordAudio = AudioSystem.getAudioInputStream(wordAudioFile);
	}

	catch (UnsupportedAudioFileException e)
	{
	    throw new IOException(e.getMessage());
	}

	this.wordModel.setId(id);
	this.wordModel.setWord(wordText);
	this.wordModel.setWordAudio(wordAudio);
    }


    public void save()
	throws IOException
    {
	this.wordController.getLogView().debug("WordStorageImpl.save()");

	PreferenceController preference = this.wordController.getPreferenceController();
	File directory = new File(preference.getWordStorageDirectory() + File.separator + this.wordModel.getId());
	directory.mkdirs();

	FileWriter textFw = new FileWriter(directory.getPath() + File.separator + WORD_TEXT);
	textFw.write(this.wordModel.getWord());
	textFw.flush();
	textFw.close();

	AudioInputStream wordAis = this.wordModel.getWordAudio();
	File wordAudioFile = new File(directory.getPath() + File.separator + WORD_AUDIO);
	if (AudioSystem.write(wordAis, AudioFileFormat.Type.WAVE, wordAudioFile) == -1)
	{
	    throw new IOException("Couldn't save " + wordAudioFile.getPath());
	}
    }

    public void delete()
	throws IOException
    {
	this.wordController.getLogView().debug("WordStorageImpl.delete()");

	String id = this.wordModel.getId();
	if (id != null)
	{
	    PreferenceController preference = this.wordController.getPreferenceController();
	    File wordDirectory = new File(preference.getWordStorageDirectory() + File.separator + id);
	    delete(wordDirectory);
	}
    }

    protected void delete(File file)
    {
	if (file.exists())
	{
	    if (file.isDirectory())
	    {
		String[] files =  file.list();
		if (files != null)
		{
		    for (int i = 0; i < files.length; i++)
		    {
			File childFile = new File(file.getPath() + File.separator + files[i]);
			delete(childFile);
		    }
		}
	    }

	    file.delete();
	}
    }
}
