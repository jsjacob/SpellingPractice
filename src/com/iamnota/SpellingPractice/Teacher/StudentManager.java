/*
 * StudentManger.java
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

package com.iamnota.SpellingPractice.Teacher;

import com.iamnota.SpellingPractice.common.ClassUtils;

import com.iamnota.SpellingPractice.Teacher.state.student.State;
import com.iamnota.SpellingPractice.Teacher.state.student.Empty;

public class StudentManager
    implements StudentController
{
    private PreferenceController preference;
    private LogView logView;

    private StudentModel studentModel;

    private StudentView studentView;
    private StudentStorage studentStorage;
    private TestStatisticsStorage testStatisticsStorage;
    private DirectedStatisticsStorage directedStatisticsStorage;

    private StudentModel reportStudentModel;
    private StudentStorage reportStudentStorage;
    private TestStatisticsStorage reportTestStatisticsStorage;
    private DirectedStatisticsStorage reportDirectedStatisticsStorage;

    private WordController wordController;

    private State state;

    public StudentManager(PreferenceController preference, LogView logView)
    {
	this.preference = preference;
	this.logView = logView;

	try
	{
	    this.studentModel = (StudentModel) ClassUtils.instantiateObject(this.preference.getStudentModelClass(), new Object[] {});

	    this.studentView = (StudentView) ClassUtils.instantiateObject(preference.getStudentViewClass(), new Object[] {this, this.studentModel});
	    this.studentView.closed();

	    this.studentStorage = (StudentStorage) ClassUtils.instantiateObject(this.preference.getStudentStorageClass(), new Object[] {this, this.studentModel});

	    this.testStatisticsStorage = (TestStatisticsStorage) ClassUtils.instantiateObject(this.preference.getTestStatisticsStorageClass(), new Object[] {this, this.studentModel});

	    this.directedStatisticsStorage = (DirectedStatisticsStorage) ClassUtils.instantiateObject(this.preference.getDirectedStatisticsStorageClass(), new Object[] {this, this.studentModel});

	    // TODO: create proxy classes to disallow saving and deleting
	    this.reportStudentModel = (StudentModel) ClassUtils.instantiateObject(this.preference.getStudentModelClass(), new Object[] {});
	    this.reportStudentStorage = (StudentStorage) ClassUtils.instantiateObject(this.preference.getStudentStorageClass(), new Object[] {this, this.reportStudentModel});
	    this.reportTestStatisticsStorage = (TestStatisticsStorage) ClassUtils.instantiateObject(this.preference.getTestStatisticsStorageClass(), new Object[] {this, this.reportStudentModel});
	    this.reportDirectedStatisticsStorage = (DirectedStatisticsStorage) ClassUtils.instantiateObject(this.preference.getDirectedStatisticsStorageClass(), new Object[] {this, this.reportStudentModel});

	}

	catch (InstantiationException e)
	{
	    this.logView.debug("StudentManager.StudentManager(): couldn't instantiate classes");
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't instantiate Student classes: " + e.getMessage());
	}

	this.wordController = null;

	this.state = Empty.getInstance();
    }

    public PreferenceController getPreferenceController()
    {
	return this.preference;
    }

    public LogView getLogView()
    {
	return this.logView;
    }

    public StudentModel getStudentModel()
    {
	return this.studentModel;
    }

    public StudentStorage getStudentStorage()
    {
	return this.studentStorage;
    }

    public StudentView getStudentView()
    {
	return this.studentView;
    }

    public TestStatisticsStorage getTestStatisticsStorage()
    {
	return this.testStatisticsStorage;
    }

    public DirectedStatisticsStorage getDirectedStatisticsStorage()
    {
	return this.directedStatisticsStorage;
    }

    public StudentModel getReportStudentModel()
    {
	return this.reportStudentModel;
    }

    public StudentStorage getReportStudentStorage()
    {
	return this.reportStudentStorage;
    }

    public TestStatisticsStorage getReportTestStatisticsStorage()
    {
	return this.reportTestStatisticsStorage;
    }

    public DirectedStatisticsStorage getReportDirectedStatisticsStorage()
    {
	return this.reportDirectedStatisticsStorage;
    }

    public WordController getWordController()
    {
	return this.wordController;
    }

    public void setWordController(WordController wordController)
    {
	this.wordController = wordController;
    }

    public synchronized boolean isSafeToQuit()
    {
	this.logView.debug("Student: " + this.state.getName() + ".isSafeToQuit()");
	return this.state.isSafeToQuit();
    }

    public synchronized void openView()
    {
	this.logView.debug("Student: " + this.state.getName() + ".openView()");
	this.state = this.state.openView(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void closeView()
    {
	this.logView.debug("Student: " + this.state.getName() + ".closeView()");
	this.state = this.state.closeView(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void newStudent()
    {
	this.logView.debug("Student: " + this.state.getName() + ".newStudent()");
	this.state = this.state.newStudent(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void load(String id)
    {
	this.logView.debug("Student: " + this.state.getName() + ".load()");
	this.state = this.state.load(this, id);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void loadDoneGood()
    {
	this.logView.debug("Student: " + this.state.getName() + ".loadDoneGood()");
	this.state = this.state.loadDoneGood(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void loadDoneBad()
    {
	this.logView.debug("Student: " + this.state.getName() + ".loadDoneBad()");
	this.state = this.state.loadDoneBad(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void unload()
    {
	this.logView.debug("Student: " + this.state.getName() + ".unload()");
	this.state = this.state.unload(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void modified()
    {
	this.logView.debug("Student: " + this.state.getName() + ".modified()");
	this.state = this.state.modified(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void save()
    {
	this.logView.debug("Student: " + this.state.getName() + ".save()");
	this.state = this.state.save(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void updateModelDone()
    {
	this.logView.debug("Student: " + this.state.getName() + ".updateModelDone()");
	this.state = this.state.updateModelDone(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void saveDone()
    {
	this.logView.debug("Student: " + this.state.getName() + ".saveDone()");
	this.state = this.state.saveDone(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void delete()
    {
	this.logView.debug("Student: " + this.state.getName() + ".delete()");
	this.state = this.state.delete(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void deleteDone()
    {
	this.logView.debug("Student: " + this.state.getName() + ".deleteDone()");
	this.state = this.state.deleteDone(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void deleteTestStatistics()
    {
	this.logView.debug("Student: " + this.state.getName() + ".deleteTestStatistics()");
	this.state = this.state.deleteTestStatistics(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void deleteTestStatisticsDone()
    {
	this.logView.debug("Student: " + this.state.getName() + ".deleteTestStatisticsDone()");
	this.state = this.state.deleteTestStatisticsDone(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void deleteDirectedStatistics()
    {
	this.logView.debug("Student: " + this.state.getName() + ".deleteDirectedStatistics()");
	this.state = this.state.deleteDirectedStatistics(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void deleteDirectedStatisticsDone()
    {
	this.logView.debug("Student: " + this.state.getName() + ".deleteDirectedStatisticsDone()");
	this.state = this.state.deleteDirectedStatisticsDone(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void showTestingStatistics()
    {
	this.logView.debug("Student: " + this.state.getName() + ".showTestingStatistics()");
	this.state = this.state.showTestingStatistics(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void showDirectedStatistics()
    {
	this.logView.debug("Student: " + this.state.getName() + ".showDirectedStatistics()");
	this.state = this.state.showDirectedStatistics(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }

    public synchronized void displayAllStudentsReport()
    {
	this.logView.debug("Student: " + this.state.getName() + ".displayAllStudentsReport()");
	this.state = this.state.displayAllStudentsReport(this);
	this.logView.debug("Student: --> " + this.state.getName());
    }
}
