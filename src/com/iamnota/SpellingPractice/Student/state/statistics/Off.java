/*
 * Off.java
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

package com.iamnota.SpellingPractice.Student.state.statistics;

import com.iamnota.SpellingPractice.Student.Controller;

import java.util.Vector;
import java.util.Random;

public class Off
    extends State
{
    private static Off me;

    private Vector wordIds;
    private Random random;

    protected Off()
    {
	this.random = new Random();
    }

    public static synchronized Off getInstance()
    {
	if (Off.me == null)
	{
	    Off.me = new Off();
	}

	return Off.me;
    }

    public String getName()
    {
	return "Off";
    }

    public boolean isShouldReload()
    {
	return false;
    }

    public State load(Controller controller)
    {
	this.wordIds = new Vector(controller.getWordStorage().getIdsToWords().keySet());
	return this;
    }

    public String getNextWordId(Controller controller)
    {
	if (!this.wordIds.isEmpty())
	{
	    int index = this.random.nextInt(this.wordIds.size());
	    String wordId = (String) this.wordIds.remove(index);
	    return wordId;
	}

	return null;
    }

    public State updateCorrect(Controller controller)
    {
	return this;
    }

    public State updateIncorrect(Controller controller)
    {
	return this;
    }

    public State save(Controller controller)
    {
	return this;
    }
}
