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

package com.iamnota.SpellingPractice.Student.jdk13;

import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.WordStorage;
import com.iamnota.SpellingPractice.Student.WordModel;
import com.iamnota.SpellingPractice.Student.PreferenceModel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.Map;
import java.util.HashMap;

public class WordStorageImpl
    implements WordStorage
{
    private Controller controller;
    private WordModel wordModel;

    private static final String WORD_TEXT = "word.txt";
    private static final String WORD_AUDIO = "word.wav";


    public WordStorageImpl(Controller controller, WordModel wordModel)
    {
	this.controller = controller;
	this.wordModel = wordModel;
    }

    public Map getIdsToWords()
    {
	HashMap map = new HashMap();

	PreferenceModel preferenceModel = this.controller.getPreferenceModel();
	File directory = new File(preferenceModel.getWordStorageDirectory());

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
	this.controller.getLogView().debug("WordStorageImpl.load()");

	String wordText = null;
	AudioInputStream wordAudio = null;

	PreferenceModel preferenceModel = this.controller.getPreferenceModel();
	File wordDirectory = new File(preferenceModel.getWordStorageDirectory() + File.separator + id);
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
}
