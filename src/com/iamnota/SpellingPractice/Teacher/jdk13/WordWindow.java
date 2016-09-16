/*
 * WordWindow.java
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

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class WordWindow
    extends JFrame
{
    private static final String TITLE_NOT_MODIFIED = "Word - Teacher - SpellingPractice";
    private static final String TITLE_MODIFIED = "Word * - Teacher - SpellingPractice";

    private WordController wordController;
    private WordModel wordModel;

    private WordMenuBar wordMenuBar;
    private WordPanel wordPanel;

    public WordWindow(WordController wordController, WordModel wordModel)
    {
	super(TITLE_NOT_MODIFIED);
	//    frame.setResizable(false);

	this.wordController = wordController;
	this.wordModel = wordModel;

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		getWordController().closeView();
	    }
	};
	addWindowListener(l);

	this.wordMenuBar = new WordMenuBar(wordController);
	setJMenuBar(this.wordMenuBar);

	Container contentPane = getContentPane();
	this.wordPanel = new WordPanel(wordController, wordModel);
	contentPane.add(this.wordPanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/2.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	hide();
    }

    protected WordController getWordController()
    {
	return this.wordController;
    }

    public void openView()
    {
	show();

	this.wordMenuBar.openView();
	this.wordPanel.openView();
    }

    public void closeView()
    {
	hide();

	this.wordMenuBar.closeView();
	this.wordPanel.closeView();
    }

    public void updateModel()
    {
	this.wordPanel.updateModel();
    }

    public void opened()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.wordMenuBar.opened();
	this.wordPanel.opened();
    }

    public void closed()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.wordMenuBar.closed();
	this.wordPanel.closed();
    }

    public void modified()
    {
	setTitle(TITLE_MODIFIED);
	this.wordMenuBar.modified();
	this.wordPanel.modified();
    }

    public void saved()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.wordMenuBar.saved();
	this.wordPanel.saved();
    }

    public void deleted()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.wordMenuBar.deleted();
	this.wordPanel.deleted();
    }

    public void recordStartedWord()
    {
	this.wordMenuBar.recordStartedWord();
	this.wordPanel.recordStartedWord();
    }

    public void recordStoppedWord()
    {
	this.wordMenuBar.recordStoppedWord();
	this.wordPanel.recordStoppedWord();
    }

    public void playStartedWordNotModified()
    {
	this.wordMenuBar.playStartedWordNotModified();
	this.wordPanel.playStartedWordNotModified();
    }

    public void playStoppedWordNotModified()
    {
	this.wordMenuBar.playStoppedWordNotModified();
	this.wordPanel.playStoppedWordNotModified();
    }

    public void playStartedWordModified()
    {
	this.wordMenuBar.playStartedWordModified();
	this.wordPanel.playStartedWordModified();
    }

    public void playStoppedWordModified()
    {
	this.wordMenuBar.playStoppedWordModified();
	this.wordPanel.playStoppedWordModified();
    }

    public void showTestingStatistics()
    {
	this.wordMenuBar.showTestingStatistics();
	this.wordPanel.showTestingStatistics();
    }

    public void showDirectedStatistics()
    {
	this.wordMenuBar.showDirectedStatistics();
	this.wordPanel.showDirectedStatistics();
    }
}
