/*
 * SpellingModelImpl.java
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

package com.iamnota.SpellingPractice.Student.jdk11;

import com.iamnota.SpellingPractice.Student.SpellingModel;

public class SpellingModelImpl
    implements SpellingModel
{
    private static final String ATTEMPTED_SPELLING_INITIAL = "";

    private String wordId;
    private String attemptedSpelling;

    public SpellingModelImpl()
    {
	clear();
    }

    public void clear()
    {
	this.wordId = null;
	clearAttemptedSpelling();
    }

    public String getWordId()
    {
	return this.wordId;
    }

    public void setWordId(String wordId)
    {
	this.wordId = wordId;
    }

    public void clearAttemptedSpelling()
    {
	this.attemptedSpelling = ATTEMPTED_SPELLING_INITIAL;
    }

    public String getAttemptedSpelling()
    {
	return this.attemptedSpelling;
    }

    public void setAttemptedSpelling(String attemptedSpelling)
    {
	this.attemptedSpelling = attemptedSpelling;
    }
}
