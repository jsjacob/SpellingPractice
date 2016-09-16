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

package com.iamnota.SpellingPractice.Student.state;

import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.LoginView;
import com.iamnota.SpellingPractice.Student.SpellingView;
import com.iamnota.SpellingPractice.Student.SpellingModel;
import com.iamnota.SpellingPractice.Student.WordStorage;

import java.io.File;
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
	private Controller controller;

	public LoadingThread(Controller controller)
	{
	    this.controller = controller;
	}

	public void run()
	{
	    this.controller.loadStarted();

	    LoginView loginView = this.controller.getLoginView();
	    loginView.hide();

	    SpellingView spellingView = this.controller.getSpellingView();
	    spellingView.clear();
	    spellingView.show();

	    SpellingModel spellingModel = this.controller.getSpellingModel();
	    spellingModel.clear();

	    // load words
	    this.controller.statisticsLoad();

	    // Load word
	    WordStorage wordStorage = this.controller.getWordStorage();

	    String wordId = this.controller.statisticsGetNextWordId();
	    if (wordId != null)
	    {
		spellingModel.setWordId(wordId);

		try
		{
		    wordStorage.load(wordId);
		    this.controller.loadStoppedSuccessfully();
		}
		
		catch (IOException e)
		{
		    // Say all words done
		    this.controller.getLogView().debug("Couldn't load word");
		    e.printStackTrace(this.controller.getLogView().getDebugStream());
		    this.controller.getLogView().error("Couldn't load word");
		    this.controller.loadStoppedUnsuccessfully();
		}
	    }
	    else
	    {
		// Say all words done
		this.controller.getLogView().debug("No words to load");
		this.controller.loadStoppedUnsuccessfully();
	    }
	}
    }

    public String getName()
    {
	return "State";
    }

    public State doAbout(Controller controller)
    {
	controller.getView().displayAbout();
	return this;
    }

    public State aboutDone(Controller controller)
    {
	return this;
    }

    public State login(Controller controller)
    {
	return this;
    }

    public State logout(Controller controller)
    {
	return this;
    }

    public State listen(Controller controller)
    {
	return this;
    }

    public State check(Controller controller)
    {
	return this;
    }

    public State timeout(Controller controller)
    {
	return this;
    }

    public State loadStarted(Controller controller)
    {
	return this;
    }

    public State loadStoppedSuccessfully(Controller controller)
    {
	return this;
    }

    public State loadStoppedUnsuccessfully(Controller controller)
    {
	return this;
    }

    public State listenStarted(Controller controller)
    {
	return this;
    }

    public State listenStopped(Controller controller)
    {
	return this;
    }

    public State showResultStarted(Controller controller)
    {
	return this;
    }

    public State showResultStopped(Controller controller)
    {
	return this;
    }

    public State showNoWordsStarted(Controller controller)
    {
	return this;
    }

    public State showNoWordsStopped(Controller controller)
    {
	return this;
    }
}
