/*
 * DirectedStatisticsStorageImpl.java
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

package com.iamnota.SpellingPractice.Student.jdk13;

import com.iamnota.SpellingPractice.Student.DirectedStatisticsStorage;
import com.iamnota.SpellingPractice.Student.DirectedStatisticsModel;
import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.PreferenceModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Set;
import java.util.Iterator;

public class DirectedStatisticsStorageImpl
    implements DirectedStatisticsStorage
{
    protected static final String DIRECTED_STATISTICS = "directed_statistics.txt";

    protected static final String PROPERTIES_HEADER = "Directed Statistics - Student - Spelling Practice";
    protected static final String PROPERTY_DIRECTED_PREFIX = "directed.";
    protected static final String PROPERTY_DIRECTED_SUFFIX_CORRECT_COUNT = ".correct";
    protected static final String PROPERTY_DIRECTED_SUFFIX_INCORRECT_STREAK_COUNT = ".incorrect.streak";

    private Controller controller;
    private DirectedStatisticsModel directedStatisticsModel;

    public DirectedStatisticsStorageImpl(Controller controller, DirectedStatisticsModel directedStatisticsModel)
    {
	this.controller = controller;
	this.directedStatisticsModel = directedStatisticsModel;
    }

    protected Controller getController()
    {
	return this.controller;
    }

    protected DirectedStatisticsModel getDirectedStatisticsModel()
    {
	return this.directedStatisticsModel;
    }

    protected File getStudentDirectedStatisticsFile(String studentId)
    {
	PreferenceModel preferenceModel = this.controller.getPreferenceModel();
	File directory = new File(preferenceModel.getStudentStorageDirectory());

	File studentDirectory = new File(directory.getPath() + File.separator + studentId);
	File studentDirectedStatisticsFile = new File(studentDirectory.getPath() + File.separator + DIRECTED_STATISTICS);

	return studentDirectedStatisticsFile;
    }

    public void load(String studentId)
	throws IOException
    {
	this.controller.getLogView().debug("DirectedStatisticsStorageImpl.load()");

	this.directedStatisticsModel.clear();
	this.directedStatisticsModel.setStudentId(studentId);

	FileInputStream fis = new FileInputStream(getStudentDirectedStatisticsFile(studentId));

	Properties properties = new Properties();
	properties.load(fis);

	for (Enumeration propertyNames = properties.propertyNames(); propertyNames.hasMoreElements();)
	{
	    String propertyName = (String) propertyNames.nextElement();

	    int wordIdDelimiter = -1;

	    wordIdDelimiter = propertyName.indexOf(PROPERTY_DIRECTED_SUFFIX_CORRECT_COUNT);
	    if (wordIdDelimiter != -1)
	    {
		String wordId = propertyName.substring(0, wordIdDelimiter);

		try
		{
		    int correctCount = Integer.parseInt(properties.getProperty(propertyName, Integer.toString(0)));
		    //		    this.controller.getLogView().debug(wordId + ": correctCount = " + correctCount);
		    this.directedStatisticsModel.setCorrectCount(wordId, correctCount);
		}

		catch (NumberFormatException e)
		{
		    this.controller.getLogView().debug("Couldn't parse " + propertyName + " \"" + properties.getProperty(propertyName) + "\"");
		    e.printStackTrace(this.controller.getLogView().getDebugStream());
		}
	    }

	    wordIdDelimiter = propertyName.indexOf(PROPERTY_DIRECTED_SUFFIX_INCORRECT_STREAK_COUNT);
	    if (wordIdDelimiter != -1)
	    {
		String wordId = propertyName.substring(0, wordIdDelimiter);

		try
		{
		    int incorrectStreakCount = Integer.parseInt(properties.getProperty(propertyName, Integer.toString(0)));
		    //		    this.controller.getLogView().debug(wordId + ": incorrectStreakCount = " + incorrectStreakCount);
		    this.directedStatisticsModel.setIncorrectStreakCount(wordId, incorrectStreakCount);
		}

		catch (NumberFormatException e)
		{
		    this.controller.getLogView().debug("Couldn't parse " + propertyName + " \"" + properties.getProperty(propertyName) + "\"");
		    e.printStackTrace(this.controller.getLogView().getDebugStream());
		}
	    }
	}
    }

    public void save()
	throws IOException
    {
	this.controller.getLogView().debug("DirectedStatisticsStorageImpl.save()");

	FileOutputStream fos = new FileOutputStream(getStudentDirectedStatisticsFile(this.directedStatisticsModel.getStudentId()));
	Properties properties = new Properties();

	Set wordIds = this.directedStatisticsModel.getWordIds();
	for (Iterator iterator = wordIds.iterator(); iterator.hasNext();)
	{
	    String wordId = (String) iterator.next();
	    int correctCount = this.directedStatisticsModel.getCorrectCount(wordId);
	    int incorrectStreakCount = this.directedStatisticsModel.getIncorrectStreakCount(wordId);

	    properties.setProperty(wordId + PROPERTY_DIRECTED_SUFFIX_CORRECT_COUNT, Integer.toString(correctCount));
	    properties.setProperty(wordId + PROPERTY_DIRECTED_SUFFIX_INCORRECT_STREAK_COUNT, Integer.toString(incorrectStreakCount));
	}

	properties.store(fos, PROPERTIES_HEADER);
    }
}
