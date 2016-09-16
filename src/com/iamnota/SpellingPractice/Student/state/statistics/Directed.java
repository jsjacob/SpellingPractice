/*
 * Directed.java
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

public class Directed
    extends State
{
    private static final float MIN_TICKET_COUNT = 1.0f;
    private static final float MULTIPLIER_ABOVE_AVERAGE_CORRECT_COUNT = 0.9f;
    private static final float MULTIPLIER_BELOW_AVERAGE_CORRECT_COUNT = 1.1f;
    private static final float MULTIPLIER_INCORRECT_STREAK_COUNT = 1.1f;

    private static Directed me;
    
    private Vector wordIdTickets;
    private Random random;

    protected Directed()
    {
	this.random = new Random();
    }

    public static synchronized Directed getInstance()
    {
	if (Directed.me == null)
	{
	    Directed.me = new Directed();
	}

	return Directed.me;
    }

    public String getName()
    {
	return "Directed";
    }

    public boolean isShouldReload()
    {
	return true;
    }

    public State load(Controller controller)
    {
	try
	{
	    controller.getDirectedStatisticsModel().clear();
	    controller.getDirectedStatisticsStorage().load(controller.getStudentModel().getId());
	}

	catch (FileNotFoundException e)
	{
	    // Ignore exception
	}
	
	catch (IOException e)
	{
	    controller.getLogView().debug("Couldn't load directed statistics");
	    e.printStackTrace(controller.getLogView().getDebugStream());
	    controller.getLogView().warning("Couldn't load directed statistics: " + e.getMessage());
	}

	// 1) create this Vector for lottery tickets

	this.wordIdTickets = new Vector();

	Set wordIds = controller.getWordStorage().getIdsToWords().keySet();

	// 2) calculate average correct count

	int totalCorrectCount = 0;
	for (Iterator iterator = wordIds.iterator(); iterator.hasNext();)
	{
	    String wordId = (String) iterator.next();
	    totalCorrectCount += controller.getDirectedStatisticsModel().getCorrectCount(wordId);
	}	
	float averageCorrectCount = (totalCorrectCount / wordIds.size());

	for (Iterator iterator = wordIds.iterator(); iterator.hasNext();)
	{
	    String wordId = (String) iterator.next();

	    // 3) base = [average correct count] tickets (min 1)

	    float ticketCount = Math.max(averageCorrectCount, MIN_TICKET_COUNT);

	    int correctCount = controller.getDirectedStatisticsModel().getCorrectCount(wordId);

	    // 4) multiply by MULTIPLIER_ABOVE_AVERAGE_CORRECT_COUNT for each correct count above average (min 1)

	    for (int i = Math.round(averageCorrectCount); i < correctCount; i++)
	    {
		ticketCount *= MULTIPLIER_ABOVE_AVERAGE_CORRECT_COUNT;
	    }
	    ticketCount = Math.max(ticketCount, MIN_TICKET_COUNT);
	    
	    // 5) multiply by MULTIPLIER_BELOW_AVERAGE_CORRECT_COUNT for each correct count below average (min 1)

	    for (int i = correctCount; i < Math.round(averageCorrectCount); i++)
	    {
		ticketCount *= MULTIPLIER_BELOW_AVERAGE_CORRECT_COUNT;
	    }
	    ticketCount = Math.max(ticketCount, MIN_TICKET_COUNT);
	    
	    // 6) multiply by MULTIPLIER_INCORRECT_STREAK_COUNT for each incorrect count streak (min 1)

	    int incorrectStreakCount = controller.getDirectedStatisticsModel().getIncorrectStreakCount(wordId);
	    for (int i = 0; i < incorrectStreakCount; i++)
	    {
		ticketCount *= MULTIPLIER_INCORRECT_STREAK_COUNT;
	    }
	    ticketCount = Math.max(ticketCount, MIN_TICKET_COUNT);
	    
	    // 7) add to this Vector of tickets (wordIds)

	    controller.getLogView().debug(wordId + ": ticketCount(" + Math.round(ticketCount) + ") correctCount(" + correctCount + ") incorrectStreakCount(" + incorrectStreakCount + ")");
	    for (int i = 0; i < Math.round(ticketCount); i++)
	    {
		this.wordIdTickets.add(wordId);
	    }
	}

	return this;
    }

    public String getNextWordId(Controller controller)
    {
	if (!this.wordIdTickets.isEmpty())
	{
	    // 1) pick random vector entry
	    int index = this.random.nextInt(this.wordIdTickets.size());
	    String wordId = (String) this.wordIdTickets.get(index);

	    // 2) remove wordid tickets from vector
	    while (this.wordIdTickets.remove(wordId));

	    this.wordIdTickets.trimToSize();

	    // 3) return wordid
	    return wordId;
	}

	return null;
    }

    public State updateCorrect(Controller controller)
    {
	String wordId = controller.getSpellingModel().getWordId();
	controller.getDirectedStatisticsModel().setCorrectCount(wordId, controller.getDirectedStatisticsModel().getCorrectCount(wordId) + 1);
	controller.getDirectedStatisticsModel().setIncorrectStreakCount(wordId, 0);
	return this;
    }

    public State updateIncorrect(Controller controller)
    {
	String wordId = controller.getSpellingModel().getWordId();
	controller.getDirectedStatisticsModel().setIncorrectStreakCount(wordId, controller.getDirectedStatisticsModel().getIncorrectStreakCount(wordId) + 1);
	return this;
    }

    public State save(Controller controller)
    {
	try
	{
	    controller.getDirectedStatisticsStorage().save();
	}
	
	catch (IOException e)
	{
	    controller.getLogView().debug("Couldn't save directed statistics");
	    e.printStackTrace(controller.getLogView().getDebugStream());
	    controller.getLogView().warning("Couldn't save directed statistics: " + e.getMessage());
	}

	return this;
    }
}
