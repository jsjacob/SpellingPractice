/*
 * ShowingResult.java
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
import com.iamnota.SpellingPractice.Student.SpellingView;

public class ShowingResult
    extends LoggedIn
{
    private static ShowingResult me;

    protected ShowingResult()
    {
	;
    }

    public static synchronized ShowingResult getInstance()
    {
	if (ShowingResult.me == null)
	{
	    ShowingResult.me = new ShowingResult();
	}

	return ShowingResult.me;
    }

    public String getName()
    {
	return "ShowingResult";
    }

    public State showResultStopped(Controller controller)
    {
	SpellingView spellingView = controller.getSpellingView();
	spellingView.showResultStopped();

	spellingView.loadStarted();
	new LoadingThread(controller).start();

	return Loading.getInstance();
    }
}
