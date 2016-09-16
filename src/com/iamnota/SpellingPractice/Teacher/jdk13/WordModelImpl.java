/*
 * WordModelImpl.java
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

import com.iamnota.SpellingPractice.Teacher.WordModel;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WordModelImpl
    implements WordModel
{
    private static final String WORD_INITIAL = "";

    private String id;
    private String word;

    private byte[] wordAudio;
    private AudioFormat wordAudioFormat;

    public void clear()
    {
	setId(null);
	setWord(WORD_INITIAL);
	
	try
	{
	    setWordAudio(null);
	}

	catch (IOException e)
	{
	    // Ignore
	}
    }

    public void setId(String id)
    {
	this.id = id;
    }

    public String getId()
    {
	return this.id;
    }

    public void setWord(String word)
    {
	if (word != null)
	{
	    this.word = word;
	}
	else
	{
	    this.word = "";
	}
    }

    public String getWord()
    {
	if (this.word != null)
	{
	    return this.word;
	}
	else
	{
	    return "";
	}
    }

    public void setWordAudio(AudioInputStream in)
	throws IOException
    {
	if (in != null)
	{
	    AudioFormat audioFormat = in.getFormat();
	    int frameSize = audioFormat.getFrameSize();
	    byte[] buffer = new byte[frameSize];
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    
	    while ((in.read(buffer)) != -1)
	    {
		baos.write(buffer);
	    }
	    
	    this.wordAudio = baos.toByteArray();
	    this.wordAudioFormat = in.getFormat();
	}
	else
	{
	    this.wordAudio = null;
	    this.wordAudioFormat = null;
	}
    }

    public AudioInputStream getWordAudio()
    {
	if (this.wordAudio != null)
	{
	    ByteArrayInputStream bais = new ByteArrayInputStream(this.wordAudio);
	    return new AudioInputStream(bais, this.wordAudioFormat, this.wordAudio.length);
	}
	else
	{
	    return null;
	}
    }
}
