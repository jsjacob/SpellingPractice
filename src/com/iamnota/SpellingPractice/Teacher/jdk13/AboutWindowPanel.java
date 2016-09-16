/*
 * AboutWindowPanel.java
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

import com.iamnota.SpellingPractice.Teacher.version;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutWindowPanel
    extends JPanel
    implements ActionListener
{
    private static final String TEXT_TYPE = "text/html";

    private static final String LABEL_OK = "OK";
    private static final String COMMAND_OK = "OK";

    private JDialog parent;

    public AboutWindowPanel(JDialog parent)
    {
	this.parent = parent;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	JEditorPane editorPane = new JEditorPane(TEXT_TYPE, "<html>" + version.getInfoHtml() + "<br>" + version.getLicenseHtml() + "</html>");
	JScrollPane editorScrollPane = new JScrollPane(editorPane);
	editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	editorScrollPane.setPreferredSize(new Dimension(400, 300));
	add(editorScrollPane);

	editorPane.setCaretPosition(0);
	editorPane.setEditable(false);

	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	JButton okButton = new JButton(LABEL_OK);
	okButton.setActionCommand(COMMAND_OK);
	okButton.addActionListener(this);
	buttonPanel.add(okButton);

	add(buttonPanel);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(COMMAND_OK))
	{
	    this.parent.dispose();
	    return;
	}
    }
}
