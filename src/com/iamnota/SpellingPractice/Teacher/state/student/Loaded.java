/*
 * Loaded.java
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

public class Loaded
    extends State
{
    protected Loaded()
    {
	;
    }
    
    public String getName()
    {
	return "Loaded";
    }

    public State newStudent(StudentController studentController)
    {
	return this;
    }

    public State load(StudentController studentController, String id)
    {
	return this;
    }

    public State unload(StudentController studentController)
    {
	StudentView studentView = studentController.getStudentView();
	studentView.closed();

	StudentModel studentModel = studentController.getStudentModel();
	studentModel.clear();

	return Empty.getInstance();
    }

    public State save(StudentController studentController)
    {
	return this;
    }

    public State delete(StudentController studentController)
    {
	return this;
    }

    public State showTestingStatistics(StudentController studentController)
    {
	String id = studentController.getStudentModel().getId();
	return super.showTestingStatistics(studentController).unload(studentController).load(studentController, id);
    }

    public State showDirectedStatistics(StudentController studentController)
    {
	String id = studentController.getStudentModel().getId();
	return super.showDirectedStatistics(studentController).unload(studentController).load(studentController, id);
    }
}
