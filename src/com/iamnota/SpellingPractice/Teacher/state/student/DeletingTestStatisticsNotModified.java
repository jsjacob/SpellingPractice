/*
 * DeletingTestStatisticsNotModified.java
 *
 * Copyright (C) 2004 John S. Jacob <jsjacob@iamnota.com>
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

public class DeletingTestStatisticsNotModified
    extends Loaded
{
    private static DeletingTestStatisticsNotModified me;

    protected DeletingTestStatisticsNotModified()
    {
	;
    }

    public static synchronized DeletingTestStatisticsNotModified getInstance()
    {
	if (DeletingTestStatisticsNotModified.me == null)
	{
	    DeletingTestStatisticsNotModified.me = new DeletingTestStatisticsNotModified();
	}

	return DeletingTestStatisticsNotModified.me;
    }

    public String getName()
    {
	return "DeletingTestStatisticsNotModified";
    }

    public State deleteTestStatisticsDone(StudentController studentController)
    {
	studentController.getStudentView().deletedTestStatistics();
	return IdleNotModified.getInstance();
    }
}
