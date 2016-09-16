/*
 * LoggedIn.java
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
import com.iamnota.SpellingPractice.Student.LoginModel;
import com.iamnota.SpellingPractice.Student.LoginView;
import com.iamnota.SpellingPractice.Student.SpellingModel;
import com.iamnota.SpellingPractice.Student.SpellingView;
import com.iamnota.SpellingPractice.Student.WordModel;
import com.iamnota.SpellingPractice.Student.WordStorage;
import com.iamnota.SpellingPractice.Student.WordAudio;

import com.iamnota.SpellingPractice.common.TextUtils;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import java.io.IOException;

public class LoggedIn
    extends State
{
    private static final String RESULT_CORRECT = "Right!";
    private static final String RESULT_INCORRECT = "No, the correct spelling is \"\\0\".";

    protected LoggedIn()
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

	    SpellingView spellingView = this.controller.getSpellingView();
	    spellingView.clear();

	    // Load word

	    SpellingModel spellingModel = this.controller.getSpellingModel();
	    spellingModel.clear();

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
		    this.controller.getLogView().debug("Couldn't load word");
		    e.printStackTrace(this.controller.getLogView().getDebugStream());
		    this.controller.getLogView().error("Couldn't load word: " + e.getMessage());
		    this.controller.loadStoppedUnsuccessfully();
		}
	    }
	    else
	    {
		if (this.controller.statisticsIsShouldReload())
		{
		    this.controller.statisticsLoad();

		    wordId = this.controller.statisticsGetNextWordId();
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
			    this.controller.getLogView().debug("Couldn't load word");
			    e.printStackTrace(this.controller.getLogView().getDebugStream());
			    this.controller.getLogView().error("Couldn't load word");
			    this.controller.loadStoppedUnsuccessfully();
			}
		    }
		    else
		    {
			// Say all words done
			this.controller.getLogView().debug("No words!");
			this.controller.loadStoppedUnsuccessfully();
		    }
		}
		else
		{
		    // Say all words done
		    this.controller.getLogView().debug("No words!");
		    this.controller.loadStoppedUnsuccessfully();
		}
	    }
	}
    }

    public String getName()
    {
	return "LoggedIn";
    }

    public State logout(Controller controller)
    {
	WordAudio wordAudio = controller.getWordAudio();
	wordAudio.playStopWord();

	LoginModel loginModel = controller.getLoginModel();
	loginModel.clear();

	LoginView loginView = controller.getLoginView();
	loginView.clear();

	SpellingView spellingView = controller.getSpellingView();
	spellingView.hide();

	loginView.show();

	return NotLoggedIn.getInstance();
    }

    public State check(Controller controller)
    {
	WordAudio wordAudio = controller.getWordAudio();
	wordAudio.playStopWord();

	SpellingView spellingView = controller.getSpellingView();
	spellingView.showResultStarted();

	SpellingModel spellingModel = controller.getSpellingModel();
	WordModel wordModel = controller.getWordModel();

	if (spellingModel.getAttemptedSpelling().trim().equalsIgnoreCase(wordModel.getWord()))
	{
	    spellingView.displayResult(RESULT_CORRECT);
	    controller.statisticsUpdateCorrect();
	    controller.statisticsSave();
	}
	else
	{
	    String incorrectString = TextUtils.replaceTokens(RESULT_INCORRECT, new String[] { wordModel.getWord() });
	    spellingView.displayResult(incorrectString);
	    controller.statisticsUpdateIncorrect();
	    controller.statisticsSave();
	}

	return ShowingResult.getInstance();
    }

    public State timeout(Controller controller)
    {
	return logout(controller);
    }
}
