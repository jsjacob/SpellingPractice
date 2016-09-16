/*
 * StudentViewImpl.java
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

import com.iamnota.SpellingPractice.Teacher.StudentView;
import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.StudentModel;

import javax.swing.SwingUtilities;


public class StudentViewImpl
    implements StudentView
{
    private StudentController studentController;
    private StudentModel studentModel;

    private StudentWindow studentWindow;

    public StudentViewImpl(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;

	this.studentWindow = new StudentWindow(studentController, studentModel);
    }

    protected void _openView()
    {
	this.studentWindow.openView();
    }

    protected void _closeView()
    {
	this.studentWindow.closeView();
    }

    protected void _updateModel()
    {
	this.studentWindow.updateModel();
	this.studentController.updateModelDone();
    }

    protected void _opened()
    {
	this.studentWindow.opened();
    }

    protected void _closed()
    {
	this.studentWindow.closed();
    }

    protected void _modified()
    {
	this.studentWindow.modified();
    }

    protected void _saved()
    {
	this.studentWindow.saved();
    }

    protected void _deleted()
    {
	this.studentWindow.deleted();
    }

    protected void _deletedTestStatistics()
    {
	this.studentWindow.deletedTestStatistics();
    }

    protected void _deletedDirectedStatistics()
    {
	this.studentWindow.deletedDirectedStatistics();
    }

    protected void _dispose()
    {
	this.studentWindow.dispose();
    }

    protected void _showTestingStatistics()
    {
	this.studentWindow.showTestingStatistics();
    }

    protected void _showDirectedStatistics()
    {
	this.studentWindow.showDirectedStatistics();
    }

    protected void _displayAllStudentsReport()
    {
	AllStudentsReportWindow allStudentsReportWindow = new AllStudentsReportWindow(this.studentController);
    }

    public void openView()
    {
	Runnable doOpenView = new Runnable()
	    {
		public void run()
		{
		    _openView();
		}
	    };

	SwingUtilities.invokeLater(doOpenView);
    }

    public void closeView()
    {
	Runnable doCloseView = new Runnable()
	    {
		public void run()
		{
		    _closeView();
		}
	    };

	SwingUtilities.invokeLater(doCloseView);
    }

    public void updateModel()
    {
	Runnable doUpdateModel = new Runnable()
	    {
		public void run()
		{
		    _updateModel();
		}
	    };

	SwingUtilities.invokeLater(doUpdateModel);
    }

    public void opened()
    {
	Runnable doOpened = new Runnable()
	    {
		public void run()
		{
		    _opened();
		}
	    };

	SwingUtilities.invokeLater(doOpened);
    }

    public void closed()
    {
	Runnable doClosed = new Runnable()
	    {
		public void run()
		{
		    _closed();
		}
	    };

	SwingUtilities.invokeLater(doClosed);
    }

    public boolean isAskSaveChanges()
    {
	return StudentSaveChangesWindow.askSaveChanges(this.studentController);
    }

    public void modified()
    {
	Runnable doModified = new Runnable()
	    {
		public void run()
		{
		    _modified();
		}
	    };

	SwingUtilities.invokeLater(doModified);
    }

    public void saved()
    {
	Runnable doSaved = new Runnable()
	    {
		public void run()
		{
		    _saved();
		}
	    };

	SwingUtilities.invokeLater(doSaved);
    }

    public void deleted()
    {
	Runnable doDeleted = new Runnable()
	    {
		public void run()
		{
		    _deleted();
		}
	    };

	SwingUtilities.invokeLater(doDeleted);
    }

    public void deletedTestStatistics()
    {
	Runnable doDeletedTestStatistics = new Runnable()
	    {
		public void run()
		{
		    _deletedTestStatistics();
		}
	    };

	SwingUtilities.invokeLater(doDeletedTestStatistics);
    }

    public void deletedDirectedStatistics()
    {
	Runnable doDeletedDirectedStatistics = new Runnable()
	    {
		public void run()
		{
		    _deletedDirectedStatistics();
		}
	    };

	SwingUtilities.invokeLater(doDeletedDirectedStatistics);
    }

    public void dispose()
    {
	Runnable doDispose = new Runnable()
	    {
		public void run()
		{
		    _dispose();
		}
	    };

	SwingUtilities.invokeLater(doDispose);
    }

    public void showTestingStatistics()
    {
	Runnable doShowTestingStatistics = new Runnable()
	    {
		public void run()
		{
		    _showTestingStatistics();
		}
	    };

	SwingUtilities.invokeLater(doShowTestingStatistics);
    }

    public void showDirectedStatistics()
    {
	Runnable doShowDirectedStatistics = new Runnable()
	    {
		public void run()
		{
		    _showDirectedStatistics();
		}
	    };

	SwingUtilities.invokeLater(doShowDirectedStatistics);
    }

    public void displayAllStudentsReport()
    {
	Runnable doDisplayAllStudentsReport = new Runnable()
	    {
		public void run()
		{
		    _displayAllStudentsReport();
		}
	    };

	SwingUtilities.invokeLater(doDisplayAllStudentsReport);
    }
}
