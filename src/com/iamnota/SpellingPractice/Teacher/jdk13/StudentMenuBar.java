/*
 * StudentMenuBar.java
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

import javax.swing.JMenuBar;

public class StudentMenuBar extends JMenuBar
{
    private StudentFileMenu studentFileMenu;
    private StudentReportMenu studentReportMenu;

    public StudentMenuBar(StudentController studentController)
    {
	this.studentFileMenu = new StudentFileMenu(studentController);
	add(this.studentFileMenu);

	this.studentReportMenu = new StudentReportMenu(studentController);
	add(this.studentReportMenu);
    }
    
    public void openView()
    {
	this.studentFileMenu.openView();
	this.studentReportMenu.openView();
    }

    public void closeView()
    {
	this.studentFileMenu.closeView();
	this.studentReportMenu.closeView();
    }

    public void opened()
    {
	this.studentFileMenu.opened();
	this.studentReportMenu.opened();
    }

    public void closed()
    {
	this.studentFileMenu.closed();
	this.studentReportMenu.closed();
    }

    public void modified()
    {
	this.studentFileMenu.modified();
	this.studentReportMenu.modified();
    }

    public void saved()
    {
	this.studentFileMenu.saved();
	this.studentReportMenu.saved();
    }

    public void deleted()
    {
	this.studentFileMenu.deleted();
	this.studentReportMenu.deleted();
    }

    public void deletedTestStatistics()
    {
	this.studentFileMenu.deletedTestStatistics();
	this.studentReportMenu.deletedTestStatistics();
    }

    public void deletedDirectedStatistics()
    {
	this.studentFileMenu.deletedDirectedStatistics();
	this.studentReportMenu.deletedDirectedStatistics();
    }

    public void showTestingStatistics()
    {
	this.studentFileMenu.showTestingStatistics();
	this.studentReportMenu.showTestingStatistics();
    }

    public void showDirectedStatistics()
    {
	this.studentFileMenu.showDirectedStatistics();
	this.studentReportMenu.showDirectedStatistics();
    }
}
