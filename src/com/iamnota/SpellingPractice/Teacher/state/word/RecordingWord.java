/*
 * RecordingWord.java
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

package com.iamnota.SpellingPractice.Teacher.state.word;

import com.iamnota.SpellingPractice.Teacher.WordController;
import com.iamnota.SpellingPractice.Teacher.WordAudio;
import com.iamnota.SpellingPractice.Teacher.WordView;

public class RecordingWord
    extends Loaded
{
    private static RecordingWord me;

    protected RecordingWord()
    {
	;
    }

    public static synchronized RecordingWord getInstance()
    {
	if (RecordingWord.me == null)
	{
	    RecordingWord.me = new RecordingWord();
	}

	return RecordingWord.me;
    }

    public String getName()
    {
	return "RecordingWord";
    }

    public State recordStopWord(WordController wordController)
    {
	WordAudio wordAudio = wordController.getWordAudio();
	wordAudio.recordStopWord();
	return this;
    }

    public State recordStartedWord(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.recordStartedWord();
	return this;
    }

    public State recordStoppedWord(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.recordStoppedWord();
	wordView.modified();
	return IdleModified.getInstance();
    }
}
