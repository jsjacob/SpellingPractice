/*
 * SpellingViewImpl.java
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

package com.iamnota.SpellingPractice.Student.jdk13;

import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.SpellingView;
import com.iamnota.SpellingPractice.Student.SpellingModel;
import com.iamnota.SpellingPractice.Student.StudentModel;

import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;

public class SpellingViewImpl
    implements SpellingView
{
    private static final String RESULT_TITLE = "SpellingPractice result";
    private static final String NO_WORDS_TITLE = "Words done";

    private static final String NO_WORDS_MESSAGE = "You have finished all of your spelling words.";

    private Controller controller;
    private SpellingModel spellingModel;

    private SpellingWindow spellingWindow;

    public SpellingViewImpl(Controller controller, SpellingModel spellingModel)
    {
	this.controller = controller;
	this.spellingModel = spellingModel;

	this.spellingWindow = new SpellingWindow(controller, spellingModel);
    }

    protected void _show()
    {
	this.spellingWindow.show();
    }

    protected void _hide()
    {
	this.spellingWindow.hide();
    }

    protected void _clear()
    {
	this.spellingWindow.clear();
    }

    protected void _displayResult(String result)
    {
	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(result);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(250, 120));

	JOptionPane.showMessageDialog(null, textAreaScrollPane, RESULT_TITLE, JOptionPane.PLAIN_MESSAGE);
	this.controller.showResultStopped();
    }

    protected void _loadStarted()
    {
	this.spellingWindow.loadStarted();
    }

    protected void _loadStopped()
    {
	this.spellingWindow.loadStopped();
    }

    protected void _listenStarted()
    {
	this.spellingWindow.listenStarted();
    }

    protected void _listenStopped()
    {
	this.spellingWindow.listenStopped();
    }

    protected void _showResultStarted()
    {
	this.spellingWindow.showResultStarted();
    }

    protected void _showResultStopped()
    {
	this.spellingWindow.showResultStopped();
    }

    protected void _showNoWordsStarted()
    {
	this.spellingWindow.showNoWordsStarted();
    }

    protected void _showNoWordsStopped()
    {
	this.spellingWindow.showNoWordsStopped();
    }

    protected void _displayNoWords()
    {
	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(NO_WORDS_MESSAGE);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(250, 120));

	JOptionPane.showMessageDialog(null, textAreaScrollPane, NO_WORDS_TITLE, JOptionPane.PLAIN_MESSAGE);
	this.controller.showNoWordsStopped();
    }

    public void show()
    {
	Runnable doShow = new Runnable()
	    {
		public void run()
		{
		    _show();
		}
	    };

	SwingUtilities.invokeLater(doShow);
    }

    public void hide()
    {
	Runnable doHide = new Runnable()
	    {
		public void run()
		{
		    _hide();
		}
	    };

	SwingUtilities.invokeLater(doHide);
    }

    public void clear()
    {
	Runnable doClear = new Runnable()
	    {
		public void run()
		{
		    _clear();
		}
	    };

	SwingUtilities.invokeLater(doClear);
    }

    protected class DoDisplayResult
	implements Runnable
    {
	private String result;

	public DoDisplayResult(String result)
	{
	    this.result = result;
	}

	public void run()
	{
	    _displayResult(this.result);
	}
    }

    public void displayResult(String result)
    {
	DoDisplayResult doDisplayResult = new DoDisplayResult(result);
	SwingUtilities.invokeLater(doDisplayResult);
    }

    public void loadStarted()
    {
	Runnable doLoadStarted = new Runnable()
	    {
		public void run()
		{
		    _loadStarted();
		}
	    };

	SwingUtilities.invokeLater(doLoadStarted);
    }

    public void loadStopped()
    {
	Runnable doLoadStopped = new Runnable()
	    {
		public void run()
		{
		    _loadStopped();
		}
	    };

	SwingUtilities.invokeLater(doLoadStopped);
    }

    public void listenStarted()
    {
	Runnable doListenStarted = new Runnable()
	    {
		public void run()
		{
		    _listenStarted();
		}
	    };

	SwingUtilities.invokeLater(doListenStarted);
    }

    public void listenStopped()
    {
	Runnable doListenStopped = new Runnable()
	    {
		public void run()
		{
		    _listenStopped();
		}
	    };

	SwingUtilities.invokeLater(doListenStopped);
    }

    public void showResultStarted()
    {
	Runnable doShowResultStarted = new Runnable()
	    {
		public void run()
		{
		    _showResultStarted();
		}
	    };

	SwingUtilities.invokeLater(doShowResultStarted);
    }

    public void showResultStopped()
    {
	Runnable doShowResultStopped = new Runnable()
	    {
		public void run()
		{
		    _showResultStopped();
		}
	    };

	SwingUtilities.invokeLater(doShowResultStopped);
    }

    public void showNoWordsStarted()
    {
	Runnable doShowNoWordsStarted = new Runnable()
	    {
		public void run()
		{
		    _showNoWordsStarted();
		}
	    };

	SwingUtilities.invokeLater(doShowNoWordsStarted);
    }

    public void showNoWordsStopped()
    {
	Runnable doShowNoWordsStopped = new Runnable()
	    {
		public void run()
		{
		    _showNoWordsStopped();
		}
	    };

	SwingUtilities.invokeLater(doShowNoWordsStopped);
    }

    protected class DoDisplayNoWords
	implements Runnable
    {
	public DoDisplayNoWords()
	{
	    ;
	}

	public void run()
	{
	    _displayNoWords();
	}
    }

    public void displayNoWords()
    {
	DoDisplayNoWords doDisplayNoWords = new DoDisplayNoWords();
	SwingUtilities.invokeLater(doDisplayNoWords);
    }
}
