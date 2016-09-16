/*
 * Idle.java
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
import com.iamnota.SpellingPractice.Student.WordAudio;

public class Idle
    extends LoggedIn
{
    private static Idle me;

    protected Idle()
    {
	;
    }

    public static synchronized Idle getInstance()
    {
	if (Idle.me == null)
	{
	    Idle.me = new Idle();
	}

	return Idle.me;
    }

    public String getName()
    {
	return "Idle";
    }

    public State listen(Controller controller)
    {
	SpellingView spellingView = controller.getSpellingView();
	spellingView.listenStarted();

	WordAudio wordAudio = controller.getWordAudio();
	wordAudio.playStartWord();

	return Listening.getInstance();
    }

    public State loadStarted(Controller controller)
    {
	SpellingView spellingView = controller.getSpellingView();
	spellingView.loadStarted();

	return Loading.getInstance();
    }

    public State listenStarted(Controller controller)
    {
	SpellingView spellingView = controller.getSpellingView();
	spellingView.listenStarted();

	return Listening.getInstance();
    }

    public State showResultStarted(Controller controller)
    {
	SpellingView spellingView = controller.getSpellingView();
	spellingView.showResultStarted();

	return ShowingResult.getInstance();
    }
}
