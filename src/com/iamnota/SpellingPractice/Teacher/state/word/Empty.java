/*
 * Empty.java
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
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.WordView;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class Empty
    extends State
{
    private static Empty me;

    protected Empty()
    {
	;
    }

    public static synchronized Empty getInstance()
    {
	if (Empty.me == null)
	{
	    Empty.me = new Empty();
	}

	return Empty.me;
    }

    public String getName()
    {
	return "Empty";
    }

    public boolean isSafeToQuit()
    {
	return true;
    }

    public State newWord(WordController wordController)
    {
	WordModel wordModel = wordController.getWordModel();

	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	String id = dateTimeFormat.format(new Date());
	wordModel.setId(id);

	WordView wordView = wordController.getWordView();
	wordView.opened();

	return IdleNotModified.getInstance();
    }

    public State load(WordController wordController, String id)
    {
	WordView wordView = wordController.getWordView();
	wordView.closed();

	WordModel wordModel = wordController.getWordModel();
	wordModel.clear();

	new LoadingThread(wordController, id).start();

	return Loading.getInstance();
    }

    public State displayAllWordsReport(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.displayAllWordsReport();
	return this;
    }
}
