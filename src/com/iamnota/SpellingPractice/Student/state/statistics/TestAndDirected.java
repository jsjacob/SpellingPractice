/*
 * TestAndDirected.java
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

package com.iamnota.SpellingPractice.Student.state.statistics;

import com.iamnota.SpellingPractice.Student.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Set;
import java.util.Iterator;

public class TestAndDirected
    extends Test
{
    private static TestAndDirected me;
    
    protected TestAndDirected()
    {
	super();
    }

    public static synchronized Test getInstance()
    {
	if (TestAndDirected.me == null)
	{
	    TestAndDirected.me = new TestAndDirected();
	}

	return TestAndDirected.me;
    }

    public String getName()
    {
	return "TestAndDirected";
    }

    public boolean isShouldReload()
    {
	return true;
    }

    public State load(Controller controller)
    {
	State nextState = _load(controller);

	if (this.wordIds.isEmpty())
	{
	    return Directed.getInstance().load(controller);
	}

	return nextState;
    }

    public String getNextWordId(Controller controller)
    {
	return _getNextWordId(controller);
    }
}
