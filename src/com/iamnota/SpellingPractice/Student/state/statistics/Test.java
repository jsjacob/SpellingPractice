/*
 * Test.java
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
import java.util.Vector;
import java.util.Random;

public class Test
    extends State
{
    private static Test me;
    
    protected Vector wordIds;
    protected Random random;

    protected Test()
    {
	this.random = new Random();
    }

    public static synchronized Test getInstance()
    {
	if (Test.me == null)
	{
	    Test.me = new Test();
	}

	return Test.me;
    }

    public String getName()
    {
	return "Test";
    }

    public boolean isShouldReload()
    {
	return false;
    }

    protected State _load(Controller controller)
    {
	try
	{
	    controller.getTestStatisticsModel().clear();
	    controller.getTestStatisticsStorage().load(controller.getStudentModel().getId());
	}

	catch (FileNotFoundException e)
	{
	    // Ignore exception
	}
	
	catch (IOException e)
	{
	    controller.getLogView().debug("Couldn't load test statistics");
	    e.printStackTrace(controller.getLogView().getDebugStream());
	    controller.getLogView().warning("Couldn't load test statistics: " + e.getMessage());
	}

	this.wordIds = new Vector();

	for (Iterator iterator = controller.getWordStorage().getIdsToWords().keySet().iterator(); iterator.hasNext();)
	{
	    String wordId = (String) iterator.next();
	    if (!controller.getTestStatisticsModel().isTested(wordId))
	    {
		this.wordIds.add(wordId);
	    }
	}

	return this;
    }

    public State load(Controller controller)
    {
	return _load(controller);
    }

    protected String _getNextWordId(Controller controller)
    {
	if (!this.wordIds.isEmpty())
	{
	    // 1) pick random vector entry
	    int index = this.random.nextInt(this.wordIds.size());
	    String wordId = (String) this.wordIds.get(index);

	    // 2) remove wordid tickets from vector
	    while (this.wordIds.remove(wordId));

	    this.wordIds.trimToSize();

	    // 3) return wordid
	    return wordId;
	}

	return null;
    }

    public String getNextWordId(Controller controller)
    {
	return _getNextWordId(controller);
    }

    public State updateCorrect(Controller controller)
    {
	String wordId = controller.getSpellingModel().getWordId();
	controller.getTestStatisticsModel().setResult(wordId, true);
	return this;
    }

    public State updateIncorrect(Controller controller)
    {
	String wordId = controller.getSpellingModel().getWordId();
	controller.getTestStatisticsModel().setResult(wordId, false);
	return this;
    }

    public State save(Controller controller)
    {
	try
	{
	    controller.getTestStatisticsStorage().save();
	}
	
	catch (IOException e)
	{
	    controller.getLogView().debug("Couldn't save test statistics");
	    e.printStackTrace(controller.getLogView().getDebugStream());
	    controller.getLogView().warning("Couldn't save test statistics: " + e.getMessage());
	}

	return this;
    }
}
