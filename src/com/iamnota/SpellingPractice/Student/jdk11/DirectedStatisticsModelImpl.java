/*
 * DirectedStatisticsModelImpl.java
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

import com.iamnota.SpellingPractice.Student.DirectedStatisticsModel;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class DirectedStatisticsModelImpl
    implements DirectedStatisticsModel
{
    private static final String STUDENT_ID_INITIAL = null;
    private static final int CORRECT_COUNT_INITIAL = 0;
    private static final int INCORRECT_STREAK_COUNT_INITIAL = 0;

    private String studentId;
    private Map correctCounts;
    private Map incorrectStreakCounts;

    public DirectedStatisticsModelImpl()
    {
	clear();
    }

    public void clear()
    {
	this.studentId = STUDENT_ID_INITIAL;
	this.correctCounts = new HashMap();
	this.incorrectStreakCounts = new HashMap();
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
	HashSet wordIds = new HashSet(this.correctCounts.keySet());
	wordIds.addAll(this.incorrectStreakCounts.keySet());
	return wordIds;
    }

    public void setCorrectCount(String wordId, int correctCount)
    {
	this.correctCounts.put(wordId, new Integer(correctCount));
    }

    public int getCorrectCount(String wordId)
    {
	Integer correctCountInteger = (Integer) this.correctCounts.get(wordId);
	if (correctCountInteger != null)
	{
	    return correctCountInteger.intValue();
	}

	return CORRECT_COUNT_INITIAL;
    }

    public void setIncorrectStreakCount(String wordId, int incorrectStreakCount)
    {
	this.incorrectStreakCounts.put(wordId, new Integer(incorrectStreakCount));
    }

    public int getIncorrectStreakCount(String wordId)
    {
	Integer incorrectStreakCountInteger = (Integer) this.incorrectStreakCounts.get(wordId);
	if (incorrectStreakCountInteger != null)
	{
	    return incorrectStreakCountInteger.intValue();
	}

	return INCORRECT_STREAK_COUNT_INITIAL;
    }
}
