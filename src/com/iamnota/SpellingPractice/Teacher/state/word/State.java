/*
 * State.java
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
import com.iamnota.SpellingPractice.Teacher.WordStorage;
import com.iamnota.SpellingPractice.Teacher.WordView;

import java.io.IOException;

public class State
{
    protected State()
    {
	;
    }

    protected class LoadingThread
	extends Thread
    {
	private WordController wordController;
	private String id;

	public LoadingThread(WordController wordController, String id)
	{
	    this.wordController = wordController;
	    this.id = id;
	}

	public void run()
	{
	    try
	    {
		WordStorage wordStorage = wordController.getWordStorage();
		wordStorage.load(this.id);
		this.wordController.loadDoneGood();
	    }

	    catch (IOException e)
	    {
		this.wordController.getLogView().debug("word.state.State.LoadingThread.run(): couldn't load word");
		e.printStackTrace(this.wordController.getLogView().getDebugStream());
		this.wordController.getLogView().error("Couldn't load word: " + e.getMessage());
		this.wordController.loadDoneBad();
	    }
	}
    }

    public String getName()
    {
	return "State";
    }

    public boolean isSafeToQuit()
    {
	return false;
    }

    public State openView(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.openView();
	return this;
    }

    public State closeView(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.closeView();
	return this;
    }

    public State newWord(WordController wordController)
    {
	return this;
    }

    public State load(WordController wordController, String id)
    {
	return this;
    }

    public State loadDoneGood(WordController wordController)
    {
	return this;
    }

    public State loadDoneBad(WordController wordController)
    {
	return this;
    }

    public State unload(WordController wordController)
    {
	return this;
    }

    public State modified(WordController wordController)
    {
	return this;
    }

    public State save(WordController wordController)
    {
	return this;
    }

    public State updateModelDone(WordController wordController)
    {
	return this;
    }

    public State saveDone(WordController wordController)
    {
	return this;
    }

    public State delete(WordController wordController)
    {
	return this;
    }

    public State deleteDone(WordController wordController)
    {
	return this;
    }

    public State recordStartWord(WordController wordController)
    {
	return this;
    }

    public State recordStopWord(WordController wordController)
    {
	return this;
    }

    public State playStartWord(WordController wordController)
    {
	return this;
    }

    public State playStopWord(WordController wordController)
    {
	return this;
    }

    public State recordStartedWord(WordController wordController)
    {
	return this;
    }

    public State recordStoppedWord(WordController wordController)
    {
	return this;
    }

    public State playStartedWord(WordController wordController)
    {
	return this;
    }

    public State playStoppedWord(WordController wordController)
    {
	return this;
    }

    public State showTestingStatistics(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.showTestingStatistics();
	return this;
    }

    public State showDirectedStatistics(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.showDirectedStatistics();
	return this;
    }

    public State displayAllWordsReport(WordController wordController)
    {
	return this;
    }
}
