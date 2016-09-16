/*
 * StudentModelImpl.java
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

import com.iamnota.SpellingPractice.Student.StudentModel;

public class StudentModelImpl
    implements StudentModel
{
    private static final String NAME_INITIAL = "";
    private static final int BIRTHDAY_MONTH_INITIAL = -1;
    private static final int BIRTHDAY_DAY_INITIAL = -1;

    private String id;
    private String name;
    private int birthdayMonth;
    private int birthdayDay;

    public StudentModelImpl()
    {
    }

    public void clear()
    {
	setId(null);
	setName(NAME_INITIAL);
	setBirthdayMonth(BIRTHDAY_MONTH_INITIAL);
	setBirthdayDay(BIRTHDAY_DAY_INITIAL);
    }

    public void setId(String id)
    {
	this.id = id;
    }

    public String getId()
    {
	return this.id;
    }

    public void setName(String name)
    {
	if (name != null)
	{
	    this.name = name;
	}
	else
	{
	    this.name = NAME_INITIAL;
	}
    }

    public String getName()
    {
	return this.name;
    }

    public void setBirthdayMonth(int month)
    {
	this.birthdayMonth = month;
    }

    public int getBirthdayMonth()
    {
	return this.birthdayMonth;
    }

    public void setBirthdayDay(int day)
    {
	this.birthdayDay = day;
    }

    public int getBirthdayDay()
    {
	return this.birthdayDay;
    }
}
