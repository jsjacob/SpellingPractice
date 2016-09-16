/*
 * Idle.java
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
import com.iamnota.SpellingPractice.Teacher.StudentModel;
import com.iamnota.SpellingPractice.Teacher.StudentStorage;
import com.iamnota.SpellingPractice.Teacher.StudentView;
import com.iamnota.SpellingPractice.Teacher.TestStatisticsStorage;
import com.iamnota.SpellingPractice.Teacher.DirectedStatisticsStorage;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import java.io.IOException;

public class Idle
    extends Loaded
{
    protected class DeletingThread
	extends Thread
    {
	private StudentController studentController;

	public DeletingThread(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void run()
	{
	    try
	    {
		StudentStorage studentStorage = this.studentController.getStudentStorage();
		studentStorage.delete();
		
		StudentView studentView = this.studentController.getStudentView();
		studentView.closed();
		
		StudentModel studentModel = this.studentController.getStudentModel();
		studentModel.clear();
	    }

	    catch (IOException e)
	    {
		this.studentController.getLogView().debug("StudentManager.delete(): couldn't delete student");
		e.printStackTrace(this.studentController.getLogView().getDebugStream());
		this.studentController.getLogView().warning("Couldn't delete student.");
	    }

	    this.studentController.deleteDone();
	}
    }

    protected class DeletingTestStatisticsThread
	extends Thread
    {
	private StudentController studentController;

	public DeletingTestStatisticsThread(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void run()
	{
	    try
	    {
		TestStatisticsStorage testStatisticsStorage = this.studentController.getTestStatisticsStorage();
		testStatisticsStorage.delete();
		
		StudentView studentView = this.studentController.getStudentView();
		studentView.deletedTestStatistics();

		if (this.studentController.getPreferenceController().getModel().getIsShowTestingStatistics())
		{
		    StudentModel studentModel = this.studentController.getStudentModel();
		    studentModel.clearStatistics();
		}
	    }

	    catch (IOException e)
	    {
		this.studentController.getLogView().debug("StudentManager.deleteTestStatistics(): couldn't delete student test statistics");
		e.printStackTrace(this.studentController.getLogView().getDebugStream());
		this.studentController.getLogView().warning("Couldn't delete student test statistics.");
	    }

	    this.studentController.deleteTestStatisticsDone();
	}
    }

    protected class DeletingDirectedStatisticsThread
	extends Thread
    {
	private StudentController studentController;

	public DeletingDirectedStatisticsThread(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void run()
	{
	    try
	    {
		DirectedStatisticsStorage directedStatisticsStorage = this.studentController.getDirectedStatisticsStorage();
		directedStatisticsStorage.delete();
		
		StudentView studentView = this.studentController.getStudentView();
		studentView.deletedDirectedStatistics();
		
		if (this.studentController.getPreferenceController().getModel().getIsShowDirectedStatistics())
		{
		    StudentModel studentModel = this.studentController.getStudentModel();
		    studentModel.clearStatistics();
		}
	    }

	    catch (IOException e)
	    {
		this.studentController.getLogView().debug("StudentManager.deleteDirectedStatistics(): couldn't delete student directed statistics");
		e.printStackTrace(this.studentController.getLogView().getDebugStream());
		this.studentController.getLogView().warning("Couldn't delete student directed statistics.");
	    }

	    this.studentController.deleteDirectedStatisticsDone();
	}
    }

    public String getName()
    {
	return "Idle";
    }

    public State newStudent(StudentController studentController)
    {
	StudentModel studentModel = studentController.getStudentModel();
	studentModel.clear();

	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	String id = dateTimeFormat.format(new Date());
	studentModel.setId(id);

	StudentView studentView = studentController.getStudentView();
	studentView.opened();

	return IdleNotModified.getInstance();
    }

    public State load(StudentController studentController, String id)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.closed();

	StudentModel studentModel = studentController.getStudentModel();
	studentModel.clear();

	new LoadingThread(studentController, id).start();

	return Loading.getInstance();
    }

    public State unload(StudentController studentController)
    {
	return super.unload(studentController);
    }

    public State modified(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.modified();

	return IdleModified.getInstance();
    }

    public State save(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.updateModel();

	return Saving.getInstance();
    }

    public State delete(StudentController studentController)
    {
	new DeletingThread(studentController).start();
	return Deleting.getInstance();
    }
}
