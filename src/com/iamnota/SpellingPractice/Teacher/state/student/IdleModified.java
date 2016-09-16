/*
 * IdleModified.java
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
import com.iamnota.SpellingPractice.Teacher.StudentView;

public class IdleModified
    extends Idle
{
    private static IdleModified me;

    protected IdleModified()
    {
	;
    }

    public static synchronized IdleModified getInstance()
    {
	if (IdleModified.me == null)
	{
	    IdleModified.me = new IdleModified();
	}

	return IdleModified.me;
    }

    public String getName()
    {
	return "IdleModified";
    }

    public State closeView(StudentController studentController)
    {
	// ask to save changes
	StudentView studentView = studentController.getStudentView();

	if (studentView.isAskSaveChanges())
	{
	    studentView.updateModel();

	    return SavingCloseView.getInstance();
	}
	else
	{
	    return super.unload(studentController).closeView(studentController);
	}
    }

    public State newStudent(StudentController studentController)
    {
	// ask to save changes
	StudentView studentView = studentController.getStudentView();

	if (studentView.isAskSaveChanges())
	{
	    studentView.updateModel();

	    return SavingNewStudent.getInstance();
	}
	else
	{
	    return super.unload(studentController).newStudent(studentController);
	}
    }

    public State load(StudentController studentController, String id)
    {
	// ask to save changes
	StudentView studentView = studentController.getStudentView();

	if (studentView.isAskSaveChanges())
	{
	    studentView.updateModel();

	    return SavingLoad.getInstance(id);
	}
	else
	{
	    return super.unload(studentController).load(studentController, id);
	}
    }

    public State unload(StudentController studentController)
    {
	// ask to save changes
	StudentView studentView = studentController.getStudentView();

	if (studentView.isAskSaveChanges())
	{
	    studentView.updateModel();

	    return SavingUnload.getInstance();
	}
	else
	{
	    return super.unload(studentController);
	}
    }

    public State deleteTestStatistics(StudentController studentController)
    {
	new DeletingTestStatisticsThread(studentController).start();
	return DeletingTestStatisticsModified.getInstance();
    }

    public State deleteDirectedStatistics(StudentController studentController)
    {
	new DeletingDirectedStatisticsThread(studentController).start();
	return DeletingDirectedStatisticsModified.getInstance();
    }
}
