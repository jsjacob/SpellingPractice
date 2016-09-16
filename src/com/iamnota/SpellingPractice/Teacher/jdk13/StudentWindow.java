/*
 * StudentWindow.java
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

import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.StudentModel;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class StudentWindow
    extends JFrame
{
    private static final String TITLE_NOT_MODIFIED = "Student - Teacher - SpellingPractice";
    private static final String TITLE_MODIFIED = "Student * - Teacher - SpellingPractice";

    private StudentController studentController;
    private StudentModel studentModel;

    private StudentMenuBar studentMenuBar;
    private StudentPanel studentPanel;

    public StudentWindow(StudentController studentController, StudentModel studentModel)
    {
	super(TITLE_NOT_MODIFIED);
	//    frame.setResizable(false);

	this.studentController = studentController;
	this.studentModel = studentModel;

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		getStudentController().closeView();
	    }
	};
	addWindowListener(l);

	this.studentMenuBar = new StudentMenuBar(studentController);
	setJMenuBar(this.studentMenuBar);

	Container contentPane = getContentPane();
	this.studentPanel = new StudentPanel(studentController, studentModel);
	contentPane.add(this.studentPanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/2.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	hide();
    }

    protected StudentController getStudentController()
    {
	return this.studentController;
    }

    public void openView()
    {
	this.studentMenuBar.openView();
	this.studentPanel.openView();

	show();
    }

    public void closeView()
    {
	hide();

	this.studentMenuBar.closeView();
	this.studentPanel.closeView();
    }

    public void updateModel()
    {
	this.studentPanel.updateModel();
    }

    public void opened()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.studentMenuBar.opened();
	this.studentPanel.opened();
    }

    public void closed()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.studentMenuBar.closed();
	this.studentPanel.closed();
    }

    public void modified()
    {
	setTitle(TITLE_MODIFIED);
	this.studentMenuBar.modified();
	this.studentPanel.modified();
    }

    public void saved()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.studentMenuBar.saved();
	this.studentPanel.saved();
    }

    public void deleted()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.studentMenuBar.deleted();
	this.studentPanel.deleted();
    }

    public void deletedTestStatistics()
    {
	this.studentMenuBar.deletedTestStatistics();
	this.studentPanel.deletedTestStatistics();
    }

    public void deletedDirectedStatistics()
    {
	this.studentMenuBar.deletedDirectedStatistics();
	this.studentPanel.deletedDirectedStatistics();
    }

    public void showTestingStatistics()
    {
	this.studentMenuBar.showTestingStatistics();
	this.studentPanel.showTestingStatistics();
    }

    public void showDirectedStatistics()
    {
	this.studentMenuBar.showDirectedStatistics();
	this.studentPanel.showDirectedStatistics();
    }
}
