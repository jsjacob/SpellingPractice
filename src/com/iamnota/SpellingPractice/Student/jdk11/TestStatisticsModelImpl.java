/*
 * TestStatisticsModelImpl.java
 *
 * Copyright (C) 2003-2004 John S. Jacob <jsjacob@iamnota.com>
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

package com.iamnota.SpellingPractice.Student.jdk11;

import com.iamnota.SpellingPractice.Student.TestStatisticsModel;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class TestStatisticsModelImpl
    implements TestStatisticsModel
{
    private static final String STUDENT_ID_INITIAL = null;

    private String studentId;
    private Map results;

    public TestStatisticsModelImpl()
    {
	clear();
    }

    public void clear()
    {
	this.studentId = STUDENT_ID_INITIAL;
	this.results = new HashMap();
    }

    public void setStudentId(String studentId)
    {
	this.studentId = studentId;
    }

    public String getStudentId()
    {
	return this.studentId;
    }

    public Set getWordIds()
    {
	HashSet wordIds = new HashSet(this.results.keySet());
	return wordIds;
    }

    public boolean isTested(String wordId)
    {
	return this.results.containsKey(wordId);
    }

    public void setResult(String wordId, boolean isCorrect)
    {
	this.results.put(wordId, new Boolean(isCorrect));
    }

    public boolean getResult(String wordId)
    {
	boolean isCorrect = false;

	if (isTested(wordId))
	{
	    Boolean isCorrectBool = (Boolean) this.results.get(wordId);
	    if (isCorrectBool != null)
	    {
		isCorrect = isCorrectBool.booleanValue();
	    }
	}

	return isCorrect;
    }
}
