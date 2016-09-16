/*
 * Idle.java
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
import com.iamnota.SpellingPractice.Teacher.WordStorage;
import com.iamnota.SpellingPractice.Teacher.WordAudio;
import com.iamnota.SpellingPractice.Teacher.WordView;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import java.io.IOException;

public class Idle
    extends Loaded
{
    private static Idle me;

    protected Idle()
    {
	;
    }

    protected class DeletingThread
	extends Thread
    {
	private WordController wordController;

	public DeletingThread(WordController wordController)
	{
	    this.wordController = wordController;
	}

	public void run()
	{
	    try
	    {
		WordStorage wordStorage = this.wordController.getWordStorage();
		wordStorage.delete();
		
		WordView wordView = this.wordController.getWordView();
		wordView.closed();
		
		WordModel wordModel = this.wordController.getWordModel();
		wordModel.clear();
	    }

	    catch (IOException e)
	    {
		this.wordController.getLogView().debug("WordManager.delete(): couldn't delete word");
		e.printStackTrace(this.wordController.getLogView().getDebugStream());
		this.wordController.getLogView().error("Couldn't delete word: " + e.getMessage());
	    }

	    this.wordController.deleteDone();
	}
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

    public State unload(WordController wordController)
    {
	return super.unload(wordController);
    }

    public State modified(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.modified();

	return IdleModified.getInstance();
    }

    public State save(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.updateModel();

	return Saving.getInstance();
    }

    public State delete(WordController wordController)
    {
	new DeletingThread(wordController).start();
	return Deleting.getInstance();
    }

    public State recordStartWord(WordController wordController)
    {
	WordAudio wordAudio = wordController.getWordAudio();
	wordAudio.recordStartWord();
	return RecordingWord.getInstance();
    }

    public State displayAllWordsReport(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.displayAllWordsReport();
	return this;
    }
}
