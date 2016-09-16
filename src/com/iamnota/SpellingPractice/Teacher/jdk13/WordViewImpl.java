/*
 * WordViewImpl.java
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

import com.iamnota.SpellingPractice.Teacher.WordController;
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.WordView;

import javax.swing.SwingUtilities;

public class WordViewImpl
    implements WordView
{
    private WordController wordController;
    private WordModel wordModel;
    
    private WordWindow wordWindow;

    public WordViewImpl(WordController wordController, WordModel wordModel)
    {
	this.wordController = wordController;
	this.wordModel = wordModel;

	this.wordWindow = new WordWindow(wordController, wordModel);
    }

    protected void _openView()
    {
	this.wordWindow.openView();
    }

    protected void _closeView()
    {
	this.wordWindow.closeView();
    }

    protected void _updateModel()
    {
	this.wordWindow.updateModel();
	this.wordController.updateModelDone();
    }

    protected void _opened()
    {
	this.wordWindow.opened();
    }

    protected void _closed()
    {
	this.wordWindow.closed();
    }

    protected void _modified()
    {
	this.wordWindow.modified();
    }

    protected void _saved()
    {
	this.wordWindow.saved();
    }

    protected void _deleted()
    {
	this.wordWindow.deleted();
    }

    protected void _recordStartedWord()
    {
	this.wordWindow.recordStartedWord();
    }

    protected void _recordStoppedWord()
    {
	this.wordWindow.recordStoppedWord();
    }

    protected void _playStartedWordNotModified()
    {
	this.wordWindow.playStartedWordNotModified();
    }

    protected void _playStoppedWordNotModified()
    {
	this.wordWindow.playStoppedWordNotModified();
    }

    protected void _playStartedWordModified()
    {
	this.wordWindow.playStartedWordModified();
    }

    protected void _playStoppedWordModified()
    {
	this.wordWindow.playStoppedWordModified();
    }

    protected void _dispose()
    {
	this.wordWindow.dispose();
    }

    protected void _showTestingStatistics()
    {
	this.wordWindow.showTestingStatistics();
    }

    protected void _showDirectedStatistics()
    {
	this.wordWindow.showDirectedStatistics();
    }

    protected void _displayAllWordsReport()
    {
	AllWordsReportWindow allWordsReportWindow = new AllWordsReportWindow(this.wordController);
    }

    public void openView()
    {
	Runnable doOpenView = new Runnable()
	    {
		public void run()
		{
		    _openView();
		}
	    };

	SwingUtilities.invokeLater(doOpenView);
    }

    public void closeView()
    {
	Runnable doCloseView = new Runnable()
	    {
		public void run()
		{
		    _closeView();
		}
	    };

	SwingUtilities.invokeLater(doCloseView);
    }

    public void updateModel()
    {
	Runnable doUpdateModel = new Runnable()
	    {
		public void run()
		{
		    _updateModel();
		}
	    };

	SwingUtilities.invokeLater(doUpdateModel);
    }

    public void opened()
    {
	Runnable doOpened = new Runnable()
	    {
		public void run()
		{
		    _opened();
		}
	    };

	SwingUtilities.invokeLater(doOpened);
    }

    public void closed()
    {
	Runnable doClosed = new Runnable()
	    {
		public void run()
		{
		    _closed();
		}
	    };

	SwingUtilities.invokeLater(doClosed);
    }

    public boolean isAskSaveChanges()
    {
	return WordSaveChangesWindow.askSaveChanges(this.wordController);
    }

    public void modified()
    {
	Runnable doModified = new Runnable()
	    {
		public void run()
		{
		    _modified();
		}
	    };

	SwingUtilities.invokeLater(doModified);
    }

    public void saved()
    {
	Runnable doSaved = new Runnable()
	    {
		public void run()
		{
		    _saved();
		}
	    };

	SwingUtilities.invokeLater(doSaved);
    }

    public void deleted()
    {
	Runnable doDeleted = new Runnable()
	    {
		public void run()
		{
		    _deleted();
		}
	    };

	SwingUtilities.invokeLater(doDeleted);
    }

    public void recordStartedWord()
    {
	Runnable doRecordStartedWord = new Runnable()
	    {
		public void run()
		{
		    _recordStartedWord();
		}
	    };

	SwingUtilities.invokeLater(doRecordStartedWord);
    }

    public void recordStoppedWord()
    {
	Runnable doRecordStoppedWord = new Runnable()
	    {
		public void run()
		{
		    _recordStoppedWord();
		}
	    };

	SwingUtilities.invokeLater(doRecordStoppedWord);
    }

    public void playStartedWordNotModified()
    {
	Runnable doPlayStartedWordNotModified = new Runnable()
	    {
		public void run()
		{
		    _playStartedWordNotModified();
		}
	    };

	SwingUtilities.invokeLater(doPlayStartedWordNotModified);
    }

    public void playStoppedWordNotModified()
    {
	Runnable doPlayStoppedWordNotModified = new Runnable()
	    {
		public void run()
		{
		    _playStoppedWordNotModified();
		}
	    };

	SwingUtilities.invokeLater(doPlayStoppedWordNotModified);
    }

    public void playStartedWordModified()
    {
	Runnable doPlayStartedWordModified = new Runnable()
	    {
		public void run()
		{
		    _playStartedWordModified();
		}
	    };

	SwingUtilities.invokeLater(doPlayStartedWordModified);
    }

    public void playStoppedWordModified()
    {
	Runnable doPlayStoppedWordModified = new Runnable()
	    {
		public void run()
		{
		    _playStoppedWordModified();
		}
	    };

	SwingUtilities.invokeLater(doPlayStoppedWordModified);
    }

    public void dispose()
    {
	Runnable doDispose = new Runnable()
	    {
		public void run()
		{
		    _dispose();
		}
	    };

	SwingUtilities.invokeLater(doDispose);
    }

    public void showTestingStatistics()
    {
	Runnable doShowTestingStatistics = new Runnable()
	    {
		public void run()
		{
		    _showTestingStatistics();
		}
	    };

	SwingUtilities.invokeLater(doShowTestingStatistics);
    }

    public void showDirectedStatistics()
    {
	Runnable doShowDirectedStatistics = new Runnable()
	    {
		public void run()
		{
		    _showDirectedStatistics();
		}
	    };

	SwingUtilities.invokeLater(doShowDirectedStatistics);
    }

    public void displayAllWordsReport()
    {
	Runnable doDisplayAllWordsReport = new Runnable()
	    {
		public void run()
		{
		    _displayAllWordsReport();
		}
	    };

	SwingUtilities.invokeLater(doDisplayAllWordsReport);
    }
}
