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

package com.iamnota.SpellingPractice.Teacher.state;

import com.iamnota.SpellingPractice.Teacher.Controller;


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
	// If a password is set, ask for it.
	String password = controller.getPreferenceController().getPassword();
	if (password != null)
	{
	    String attemptedPassword = null;

	    do
	    {
		attemptedPassword = controller.getView().askPassword();
		if (!password.equals(attemptedPassword))
		{
		    controller.getView().showBadPassword();
		}
	    }
	    while (!password.equals(attemptedPassword));
	}

	controller.getView().show();
	return Running.getInstance();
    }
}
