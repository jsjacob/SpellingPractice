/*
 * IdleNotModified.java
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

public class IdleNotModified
    extends Idle
{
    private static IdleNotModified me;

    protected IdleNotModified()
    {
	;
    }

    public static synchronized IdleNotModified getInstance()
    {
	if (IdleNotModified.me == null)
	{
	    IdleNotModified.me = new IdleNotModified();
	}

	return IdleNotModified.me;
    }

    public String getName()
    {
	return "IdleNotModified";
    }

    public boolean isSafeToQuit()
    {
	return true;
    }

    public State deleteTestStatistics(StudentController studentController)
    {
	new DeletingTestStatisticsThread(studentController).start();
	return DeletingTestStatisticsNotModified.getInstance();
    }

    public State deleteDirectedStatistics(StudentController studentController)
    {
	new DeletingDirectedStatisticsThread(studentController).start();
	return DeletingDirectedStatisticsNotModified.getInstance();
    }
}
