/*
 * BadPasswordWindow.java
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

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;


public class BadPasswordWindow
{
    private static final String TITLE = "Bad password";

    private static final String MESSAGE = "The password you entered was not correct.";

    public static void showBadPassword(Controller controller)
    {
	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(MESSAGE);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(400, 120));

	JOptionPane.showMessageDialog(null, textAreaScrollPane, TITLE, JOptionPane.ERROR_MESSAGE, null);
    }
}
