/*
 * Saving.java
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
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import java.io.IOException;

public class Saving
    extends Loaded
{
    private static Saving me;

    protected Saving()
    {
	;
    }

    public static synchronized Saving getInstance()
    {
	if (Saving.me == null)
	{
	    Saving.me = new Saving();
	}

	return Saving.me;
    }

    protected class SavingThread
	extends Thread
    {
	private StudentController studentController;

	public SavingThread(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void run()
	{
	    try
	    {
		StudentStorage studentStorage = studentController.getStudentStorage();
		studentStorage.save();

		PreferenceController preference = studentController.getPreferenceController();
		preference.save();
	    }

	    catch (IOException e)
	    {
		this.studentController.getLogView().debug("student.state.Saving.SavingThread.run(): couldn't save student");
		e.printStackTrace(this.studentController.getLogView().getDebugStream());
		this.studentController.getLogView().error("Couldn't save student: " + e.getMessage());
	    }

	    this.studentController.saveDone();
	}
    }

    public String getName()
    {
	return "Saving";
    }

    public State updateModelDone(StudentController studentController)
    {
	new SavingThread(studentController).start();
	return this;
    }

    public State saveDone(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.saved();

	return IdleNotModified.getInstance();
    }
}
