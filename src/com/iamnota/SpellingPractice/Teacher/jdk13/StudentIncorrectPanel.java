/*
 * StudentIncorrectPanel.java
 *
 * Copyright (C) 2004 John S. Jacob <jsjacob@iamnota.com>
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

import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.StudentModel;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;

import java.util.Vector;
import java.util.Iterator;
import java.util.Collections;

public class StudentIncorrectPanel
    extends JPanel
{
    private StudentController studentController;
    private StudentModel studentModel;

    private JTextArea textArea;
    private JScrollPane scrollPane;

    public StudentIncorrectPanel(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;

	PreferenceController preference = this.studentController.getPreferenceController();

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	this.textArea = new JTextArea();
	this.textArea.setCaretPosition(0);
	this.textArea.setEditable(false);
	this.scrollPane = new JScrollPane(this.textArea);
	add(this.scrollPane);
    }

    public void updateModel()
    {
    }

    public void opened()
    {
	showStatistics();
    }

    public void closed()
    {
    }

    public void modified()
    {
    }

    public void saved()
    {
    }

    public void deletedTestStatistics()
    {
	showStatistics();
    }

    public void deletedDirectedStatistics()
    {
	showStatistics();
    }

    public void showTestingStatistics()
    {
	showStatistics();
    }

    public void showDirectedStatistics()
    {
	showStatistics();
    }

    protected void showStatistics()
    {
	String lineSeparator = System.getProperty("line.separator");

	String text = "";

	Vector words = new Vector(this.studentModel.getIncorrectIdsToWords().values());
	Collections.sort(words);
	for (Iterator i = words.iterator(); i.hasNext();)
	{
	    String word = (String) i.next();
	    text += (word + lineSeparator);
	}

	this.textArea.setText(text);
    }
}
