/*
 * WordMenuBar.java
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

import javax.swing.JMenuBar;

public class WordMenuBar extends JMenuBar
{
    private WordFileMenu wordFileMenu;
    private WordReportMenu wordReportMenu;

    public WordMenuBar(WordController wordController)
    {
	this.wordFileMenu = new WordFileMenu(wordController);
	add(this.wordFileMenu);

	this.wordReportMenu = new WordReportMenu(wordController);
	add(this.wordReportMenu);
    }

    public void openView()
    {
	this.wordFileMenu.openView();
	this.wordReportMenu.openView();
    }

    public void closeView()
    {
	this.wordFileMenu.closeView();
	this.wordReportMenu.closeView();
    }

    public void opened()
    {
	this.wordFileMenu.opened();
	this.wordReportMenu.opened();
    }

    public void closed()
    {
	this.wordFileMenu.closed();
	this.wordReportMenu.closed();
    }

    public void modified()
    {
	this.wordFileMenu.modified();
	this.wordReportMenu.modified();
    }

    public void saved()
    {
	this.wordFileMenu.saved();
	this.wordReportMenu.saved();
    }

    public void deleted()
    {
	this.wordFileMenu.deleted();
	this.wordReportMenu.deleted();
    }

    public void recordStartedWord()
    {
	this.wordFileMenu.recordStartedWord();
	this.wordReportMenu.recordStartedWord();
    }

    public void recordStoppedWord()
    {
	this.wordFileMenu.recordStoppedWord();
	this.wordReportMenu.recordStoppedWord();
    }

    public void playStartedWordNotModified()
    {
	this.wordFileMenu.playStartedWordNotModified();
	this.wordReportMenu.playStartedWordNotModified();
    }

    public void playStoppedWordNotModified()
    {
	this.wordFileMenu.playStoppedWordNotModified();
	this.wordReportMenu.playStoppedWordNotModified();
    }

    public void playStartedWordModified()
    {
	this.wordFileMenu.playStartedWordModified();
	this.wordReportMenu.playStartedWordModified();
    }

    public void playStoppedWordModified()
    {
	this.wordFileMenu.playStoppedWordModified();
	this.wordReportMenu.playStoppedWordModified();
    }

    public void showTestingStatistics()
    {
	this.wordFileMenu.showTestingStatistics();
	this.wordReportMenu.showTestingStatistics();
    }

    public void showDirectedStatistics()
    {
	this.wordFileMenu.showDirectedStatistics();
	this.wordReportMenu.showDirectedStatistics();
    }
}
