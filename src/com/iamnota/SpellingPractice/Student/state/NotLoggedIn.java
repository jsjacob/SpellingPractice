/*
 * NotLoggedIn.java
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

package com.iamnota.SpellingPractice.Student.state;

import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.LoginModel;
import com.iamnota.SpellingPractice.Student.LoginView;
import com.iamnota.SpellingPractice.Student.StudentStorage;
import com.iamnota.SpellingPractice.Student.StudentModel;
import com.iamnota.SpellingPractice.Student.SpellingModel;
import com.iamnota.SpellingPractice.Student.WordAudio;

import com.iamnota.SpellingPractice.common.TextUtils;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Date;

import java.text.SimpleDateFormat;

import java.io.IOException;

public class NotLoggedIn
    extends State
{
    private static final String TEMPLATE_LOGIN_BAD = "\\0 is not \\1's birthday.";

    private static NotLoggedIn me;

    protected NotLoggedIn()
    {
	;
    }

    public static synchronized NotLoggedIn getInstance()
    {
	if (NotLoggedIn.me == null)
	{
	    NotLoggedIn.me = new NotLoggedIn();
	}

	return NotLoggedIn.me;
    }

    public String getName()
    {
	return "NotLoggedIn";
    }

    public State login(Controller controller)
    {
	LoginModel loginModel = controller.getLoginModel();
	StudentStorage studentStorage = controller.getStudentStorage();

	try
	{
	    Map idsToStudents = studentStorage.getIdsToStudents();
	    String id = loginModel.getId();
	    studentStorage.load(id);
	}

	catch (IOException e)
	{
	    controller.getLogView().debug("Couldn't get student information");
	    e.printStackTrace(controller.getLogView().getDebugStream());
	    controller.getLogView().error("Couldn't get student information");
	    return this;
	}

	StudentModel studentModel = controller.getStudentModel();
	if ((loginModel.getBirthdayMonth() == studentModel.getBirthdayMonth()) &&
	    (loginModel.getBirthdayDay() == studentModel.getBirthdayDay()))
	{
	    new LoadingThread(controller).start();

	    return Loading.getInstance();
	}

	Calendar birthdayCalendar = Calendar.getInstance();
	birthdayCalendar.set(Calendar.MONTH, loginModel.getBirthdayMonth());
	birthdayCalendar.set(Calendar.DAY_OF_MONTH, loginModel.getBirthdayDay());
	Date birthdayDate = birthdayCalendar.getTime();

	SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d");
	String dateString = dateFormat.format(birthdayDate);

	String badLoginString = TextUtils.replaceTokens(TEMPLATE_LOGIN_BAD, new String[] { dateString, loginModel.getName() });
	
	LoginView loginView = controller.getLoginView();
	loginView.displayMessage(badLoginString);

	return this;
    }
}
