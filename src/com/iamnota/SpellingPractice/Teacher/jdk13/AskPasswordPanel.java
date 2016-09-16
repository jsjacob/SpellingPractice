/*
 * AskPasswordPanel.java
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AskPasswordPanel
    extends JPanel
    implements ActionListener
{
    private static final String PROMPT = "Please enter teacher password";

    private static final String COMMAND_START = "START";
    private static final String COMMAND_QUIT = "QUIT";

    private static final String LABEL_START = "Start";
    private static final String LABEL_QUIT = "Quit";

    private Controller controller;
    private AskPasswordWindow parent;

    private JPasswordField passwordField;

    public AskPasswordPanel(Controller controller, AskPasswordWindow parent)
    {
	this.controller = controller;
	this.parent = parent;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	JPanel passwordPanel = new JPanel();
	passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));

	JLabel prompt = new JLabel(PROMPT);
	passwordPanel.add(prompt);
	
	this.passwordField = new JPasswordField(15);
	passwordPanel.add(this.passwordField);

	add(passwordPanel);

	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	JButton startButton = new JButton(LABEL_START);
	startButton.setActionCommand(COMMAND_START);
	startButton.addActionListener(this);
	buttonPanel.add(startButton);

	JButton quitButton = new JButton(LABEL_QUIT);
	quitButton.setActionCommand(COMMAND_QUIT);
	quitButton.addActionListener(this);
	buttonPanel.add(quitButton);

	add(buttonPanel);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(COMMAND_START))
	{
	    String password = new String(this.passwordField.getPassword());
	    this.parent.setPassword(password);

	    this.parent.dispose();

	    return;
	}

	if (action.equals(COMMAND_QUIT))
	{
	    this.parent.dispose();
	    this.controller.quit();
	    return;
	}
    }
}
