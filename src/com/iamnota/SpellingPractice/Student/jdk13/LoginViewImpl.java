/*
 * LoginViewImpl.java
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
import com.iamnota.SpellingPractice.Student.LoginView;
import com.iamnota.SpellingPractice.Student.LoginModel;
import com.iamnota.SpellingPractice.Student.PreferenceModel;

import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;


public class LoginViewImpl
    implements LoginView
{
    private static final String MESSAGE_TITLE = "SpellingPractice message";

    private Controller controller;
    private LoginModel loginModel;

    private LoginWindow loginWindow;

    public LoginViewImpl(Controller controller, LoginModel loginModel)
    {
	this.controller = controller;
	this.loginModel = loginModel;

	this.loginWindow = new LoginWindow(controller, loginModel);
    }

    protected void _show()
    {
	this.loginWindow.show();
    }

    protected void _hide()
    {
	this.loginWindow.hide();
    }

    protected void _clear()
    {
	this.loginWindow.clear();
    }

    protected void _displayMessage(String message)
    {
	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(message);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(250, 120));

	JOptionPane.showMessageDialog(null, textAreaScrollPane, MESSAGE_TITLE, JOptionPane.PLAIN_MESSAGE);
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

    protected class DoDisplayMessage
	implements Runnable
    {
	private String message;

	public DoDisplayMessage(String message)
	{
	    this.message = message;
	}

	public void run()
	{
	    _displayMessage(this.message);
	}
    }

    public void displayMessage(String message)
    {
	DoDisplayMessage doDisplayMessage = new DoDisplayMessage(message);
	SwingUtilities.invokeLater(doDisplayMessage);
    }
}
