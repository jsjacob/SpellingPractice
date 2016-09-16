/*
 * StudentSummaryPanel.java
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
import com.iamnota.SpellingPractice.Teacher.WordController;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Dimension;

public class StudentSummaryPanel
    extends JPanel
{
    private static final String NAME_LABEL = "Name: ";
    private static final String CORRECT_LABEL = "Correct: ";
    private static final String INCORRECT_LABEL = "Incorrect: ";
    private static final String REMAINING_LABEL = "Remaining: ";
    private static final String TOTAL_LABEL = "Total: ";
    
    private StudentController studentController;
    private StudentModel studentModel;

    private JLabel name;
    private JLabel countCorrect;
    private JLabel countIncorrect;
    private JLabel countRemaining;
    private JLabel countTotal;

    public StudentSummaryPanel(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;

	PreferenceController preference = this.studentController.getPreferenceController();

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	this.name = new JLabel(NAME_LABEL);
	add(this.name);

	this.countCorrect = new JLabel(CORRECT_LABEL);
	add(this.countCorrect);

	this.countIncorrect = new JLabel(INCORRECT_LABEL);
	add(this.countIncorrect);

	this.countRemaining = new JLabel(REMAINING_LABEL);
	add(this.countRemaining);

	this.countTotal = new JLabel(TOTAL_LABEL);
	add(this.countTotal);
    }

    public void updateModel()
    {
    }

    public void opened()
    {
	this.name.setText(NAME_LABEL + this.studentModel.getName());

	showStatistics();
    }

    public void closed()
    {
	this.name.setText(NAME_LABEL);
	this.countCorrect.setText(CORRECT_LABEL);
	this.countIncorrect.setText(INCORRECT_LABEL);
	this.countRemaining.setText(REMAINING_LABEL);
	this.countTotal.setText(TOTAL_LABEL);
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
	this.countCorrect.setText(CORRECT_LABEL + ("" + this.studentModel.getCorrectIdsToWords().size()));
	this.countIncorrect.setText(INCORRECT_LABEL + ("" + this.studentModel.getIncorrectIdsToWords().size()));
	WordController wordController = this.studentController.getWordController();
	int total = wordController.getWordStorage().getIdsToWords().size();
	this.countRemaining.setText(REMAINING_LABEL + ("" + (total - this.studentModel.getCorrectIdsToWords().size() - this.studentModel.getIncorrectIdsToWords().size())));
	this.countTotal.setText(TOTAL_LABEL + ("" + total));
    }
}
