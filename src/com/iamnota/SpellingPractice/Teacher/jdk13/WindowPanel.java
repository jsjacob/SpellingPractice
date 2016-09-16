/*
 * WindowPanel.java
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

import com.iamnota.SpellingPractice.Teacher.Controller;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WindowPanel
    extends JPanel
    implements ActionListener
{
    private static final String LABEL_WORDS = "Words";
    private static final String COMMAND_WORDS = "WORDS";
    
    private static final String LABEL_STUDENTS = "Students";
    private static final String COMMAND_STUDENTS = "STUDENTS";

    private static final String LABEL_PREFERENCES = "Preferences";
    private static final String COMMAND_PREFERENCES = "PREFERENCES";

    private static final String LABEL_ABOUT = "About";
    private static final String COMMAND_ABOUT = "ABOUT";
    
    private static final String LABEL_QUIT = "Quit";
    private static final String COMMAND_QUIT = "QUIT";

    private Controller controller;

    public WindowPanel(Controller controller)
    {
	this.controller = controller;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	JButton wordsButton = new JButton(LABEL_WORDS);
	wordsButton.setActionCommand(COMMAND_WORDS);
	wordsButton.addActionListener(this);
	add(wordsButton);

	JButton studentsButton = new JButton(LABEL_STUDENTS);
	studentsButton.setActionCommand(COMMAND_STUDENTS);
	studentsButton.addActionListener(this);
	add(studentsButton);

	JButton preferencesButton = new JButton(LABEL_PREFERENCES);
	preferencesButton.setActionCommand(COMMAND_PREFERENCES);
	preferencesButton.addActionListener(this);
	add(preferencesButton);

	JButton aboutButton = new JButton(LABEL_ABOUT);
	aboutButton.setActionCommand(COMMAND_ABOUT);
	aboutButton.addActionListener(this);
	add(aboutButton);

	JButton quitButton = new JButton(LABEL_QUIT);
	quitButton.setActionCommand(COMMAND_QUIT);
	quitButton.addActionListener(this);
	add(quitButton);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(COMMAND_WORDS))
	{
	    this.controller.doWords();
	    return;
	}
	
	if (action.equals(COMMAND_STUDENTS))
	{
	    this.controller.doStudents();
	    return;
	}
	
	if (action.equals(COMMAND_PREFERENCES))
	{
	    this.controller.doPreferences();
	    return;
	}

	if (action.equals(COMMAND_ABOUT))
	{
	    this.controller.doAbout();
	    return;
	}

	if (action.equals(COMMAND_QUIT))
	{
	    this.controller.quit();
	    return;
	}
    }
}
