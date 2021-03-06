/*
 * PreferenceSaveChangesWindow.java
 *
 * Copyright (C) 2003-2004 John S. Jacob <jsjacob@iamnota.com>
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

import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import com.iamnota.SpellingPractice.common.TextUtils;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;

public class PreferenceSaveChangesWindow
{
    private static final String TITLE = "Save changes to preferences";

    private static final String TEMPLATE_CONFIRMATION = "Save preference changes?";

    private static final String YES = "Yes";
    private static final String NO = "No";

    public static boolean askSaveChanges(PreferenceController preferenceController)
    {
	String confirmation = TEMPLATE_CONFIRMATION;

	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(confirmation);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(400, 120));

	Object[] options = new Object[] { YES, NO };

	boolean isSaveChanges = (JOptionPane.showOptionDialog(null, textAreaScrollPane, TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, NO) == 0);
	
	return isSaveChanges;
    }
}
