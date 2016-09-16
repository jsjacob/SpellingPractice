/*
 * State.java
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

package com.iamnota.SpellingPractice.Teacher.state.student;

import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.StudentStorage;
import com.iamnota.SpellingPractice.Teacher.StudentView;
import com.iamnota.SpellingPractice.Teacher.TestStatisticsStorage;
import com.iamnota.SpellingPractice.Teacher.DirectedStatisticsStorage;

import java.io.IOException;

public class State
{
    protected State()
    {
	;
    }

    protected class LoadingThread
	extends Thread
    {
	private StudentController studentController;
	private String id;

	public LoadingThread(StudentController studentController, String id)
	{
	    this.studentController = studentController;
	    this.id = id;
	}

	public void run()
	{
	    try
	    {
		StudentStorage studentStorage = studentController.getStudentStorage();
		studentStorage.load(this.id);

		if (studentController.getPreferenceController().getModel().getIsShowTestingStatistics())
		{
		    TestStatisticsStorage testStatisticsStorage = studentController.getTestStatisticsStorage();
		    testStatisticsStorage.load();
		}

		if (studentController.getPreferenceController().getModel().getIsShowDirectedStatistics())
		{
		    DirectedStatisticsStorage directedStatisticsStorage = studentController.getDirectedStatisticsStorage();
		    directedStatisticsStorage.load();
		}

		this.studentController.loadDoneGood();
	    }

	    catch (IOException e)
	    {
		this.studentController.getLogView().debug("state.word.State.LoadingThread.run(): couldn't load student");
		e.printStackTrace(this.studentController.getLogView().getDebugStream());
		this.studentController.getLogView().error("Couldn't load student: " + e.getMessage());
		this.studentController.loadDoneBad();
	    }
	}
    }

    public String getName()
    {
	return "State";
    }

    public boolean isSafeToQuit()
    {
	return false;
    }

    public State openView(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.openView();
	return this;
    }

    public State closeView(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.closeView();
	return this;
    }

    public State newStudent(StudentController studentController)
    {
	return this;
    }

    public State load(StudentController studentController, String id)
    {
	return this;
    }

    public State loadDoneGood(StudentController studentController)
    {
	return this;
    }

    public State loadDoneBad(StudentController studentController)
    {
	return this;
    }

    public State unload(StudentController studentController)
    {
	return this;
    }

    public State modified(StudentController studentController)
    {
	return this;
    }

    public State save(StudentController studentController)
    {
	return this;
    }

    public State updateModelDone(StudentController studentController)
    {
	return this;
    }

    public State saveDone(StudentController studentController)
    {
	return this;
    }

    public State delete(StudentController studentController)
    {
	return this;
    }

    public State deleteDone(StudentController studentController)
    {
	return this;
    }

    public State deleteTestStatistics(StudentController studentController)
    {
	return this;
    }

    public State deleteTestStatisticsDone(StudentController studentController)
    {
	return this;
    }

    public State deleteDirectedStatistics(StudentController studentController)
    {
	return this;
    }

    public State deleteDirectedStatisticsDone(StudentController studentController)
    {
	return this;
    }

    public State showTestingStatistics(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.showTestingStatistics();
	return this;
    }

    public State showDirectedStatistics(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.showDirectedStatistics();
	return this;
    }

    public State displayAllStudentsReport(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.displayAllStudentsReport();
	return this;
    }
}
