/*
 * Starting.java
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
import com.iamnota.SpellingPractice.Student.PreferenceModel;
import com.iamnota.SpellingPractice.Student.StudentStorage;

import java.io.File;
import java.io.IOException;

public class Starting
    extends State
{
    private static Starting me;

    protected Starting()
    {
	;
    }

    public static synchronized Starting getInstance()
    {
	if (Starting.me == null)
	{
	    Starting.me = new Starting();
	}

	return Starting.me;
    }

    public String getName()
    {
	return "Starting";
    }

    public State aboutDone(Controller controller)
    {
	// Check for auto-login
	PreferenceModel preferenceModel = controller.getPreferenceModel();
	String studentAutoLoginId = preferenceModel.getStudentAutoLoginId();
	if ((studentAutoLoginId != null) && new File(preferenceModel.getStudentStorageDirectory() + File.separator + studentAutoLoginId).exists())
	{
	    StudentStorage studentStorage = controller.getStudentStorage();
	    
	    try
	    {
		studentStorage.load(studentAutoLoginId);
	    }
	    
	    catch (IOException e)
	    {
		controller.getLogView().debug("Couldn't get student information");
		e.printStackTrace(controller.getLogView().getDebugStream());
		controller.getLogView().error("Couldn't get student information");
		return this;
	    }

	    new LoadingThread(controller).start();

	    return Loading.getInstance();
	}
	else
	{
	    controller.getLoginView().clear();
	    controller.getLoginView().show();
	    return NotLoggedIn.getInstance();
	}
    }
}
