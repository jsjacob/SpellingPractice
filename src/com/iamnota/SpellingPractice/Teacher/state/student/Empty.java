/*
 * Empty.java
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
import com.iamnota.SpellingPractice.Teacher.StudentView;

import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class Empty
    extends State
{
    private static Empty me;

    protected Empty()
    {
	;
    }

    public static synchronized Empty getInstance()
    {
	if (Empty.me == null)
	{
	    Empty.me = new Empty();
	}

	return Empty.me;
    }

    public String getName()
    {
	return "Empty";
    }

    public boolean isSafeToQuit()
    {
	return true;
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
}
