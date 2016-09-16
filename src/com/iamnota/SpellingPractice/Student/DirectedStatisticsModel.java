/*
 * DirectedStatisticsModel.java
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

package com.iamnota.SpellingPractice.Student;

import java.util.Set;

public interface DirectedStatisticsModel
{
    public void clear();

    public void setStudentId(String studentId);
    public String getStudentId();

    public Set getWordIds();

    public void setCorrectCount(String wordId, int correctCount);
    public int getCorrectCount(String wordId);

    public void setIncorrectStreakCount(String wordId, int incorrectStreakCount);
    public int getIncorrectStreakCount(String wordId);
}
