/*
 * SpellingPanel.java
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
import com.iamnota.SpellingPractice.Student.SpellingModel;
import com.iamnota.SpellingPractice.Student.PreferenceModel;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Map;
import java.util.TreeSet;

public class SpellingPanel
    extends JPanel
    implements ActionListener
{
    private static final String WORD_TITLE = "Word";

    private static final String COMMAND_LISTEN = "LISTEN";
    private static final String COMMAND_CHECK = "CHECK";

    private static final String INITIAL_SPELLING_ATTEMPT = "";
    private static final String INITIAL_SPELLING_STATUS = "";

    private static final String LISTEN_LABEL = "Listen";
    private static final String CHECK_LABEL = "Check";
    
    private static final float WORD_FONT_SIZE_MULTIPLIER = 2.0F;

    private JTextField attemptedSpellingText;
    private JButton listenButton;
    private JButton checkButton;

    private Controller controller;
    private SpellingModel spellingModel;

    public SpellingPanel(Controller controller, SpellingModel spellingModel)
    {
	this.controller = controller;
	this.spellingModel = spellingModel;

	PreferenceModel preferenceModel = this.controller.getPreferenceModel();

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
	topPanel.setBorder(BorderFactory.createTitledBorder(WORD_TITLE));
	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

	this.attemptedSpellingText = new JTextField(INITIAL_SPELLING_ATTEMPT, 15);
	Font attemptedSpellingFont = this.attemptedSpellingText.getFont();
	this.attemptedSpellingText.setFont(attemptedSpellingFont.deriveFont(attemptedSpellingFont.getSize() * WORD_FONT_SIZE_MULTIPLIER));
	this.attemptedSpellingText.setRequestFocusEnabled(true);
	//        this.attemptedSpellingText.setPreferredSize(new Dimension(200,25));
        topPanel.add(this.attemptedSpellingText);

        JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	this.listenButton = new JButton(LISTEN_LABEL);
	listenButton.setActionCommand(COMMAND_LISTEN);
	listenButton.addActionListener(this);
	buttonPanel.add(listenButton);
	
	this.checkButton = new JButton(CHECK_LABEL);
	checkButton.setActionCommand(COMMAND_CHECK);
	checkButton.addActionListener(this);
	buttonPanel.add(checkButton);

	topPanel.add(buttonPanel);

        add(topPanel);
    }

    public void clear()
    {
	this.attemptedSpellingText.setText(INITIAL_SPELLING_ATTEMPT);
    }

    public void loadStarted()
    {
	this.attemptedSpellingText.setEnabled(false);
	this.listenButton.setEnabled(false);
	this.checkButton.setEnabled(false);
    }

    public void loadStopped()
    {
	this.attemptedSpellingText.setEnabled(true);
	this.listenButton.setEnabled(true);
	this.checkButton.setEnabled(true);
	this.attemptedSpellingText.requestFocus();
    }

    public void listenStarted()
    {
	this.attemptedSpellingText.setEnabled(true);
	this.listenButton.setEnabled(false);
	this.checkButton.setEnabled(true);
	this.attemptedSpellingText.requestFocus();
    }

    public void listenStopped()
    {
	this.attemptedSpellingText.setEnabled(true);
	this.listenButton.setEnabled(true);
	this.checkButton.setEnabled(true);
	this.attemptedSpellingText.requestFocus();
    }

    public void showResultStarted()
    {
	this.attemptedSpellingText.setEnabled(false);
	this.listenButton.setEnabled(false);
	this.checkButton.setEnabled(false);
    }

    public void showResultStopped()
    {
	this.attemptedSpellingText.setEnabled(true);
	this.listenButton.setEnabled(true);
	this.checkButton.setEnabled(true);
    }

    public void showNoWordsStarted()
    {
	this.attemptedSpellingText.setEnabled(false);
	this.listenButton.setEnabled(false);
	this.checkButton.setEnabled(false);
    }

    public void showNoWordsStopped()
    {
	this.attemptedSpellingText.setEnabled(false);
	this.listenButton.setEnabled(false);
	this.checkButton.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(COMMAND_CHECK))
	{
	    String attemptedSpelling = (String) this.attemptedSpellingText.getText();
	    this.spellingModel.setAttemptedSpelling(attemptedSpelling);
	    this.controller.check();

	    return;
	}
	
	if (action.equals(COMMAND_LISTEN))
	{
	    this.controller.listen();

	    return;
	}
    }
}
