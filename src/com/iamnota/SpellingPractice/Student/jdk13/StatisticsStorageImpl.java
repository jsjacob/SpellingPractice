/*
 * StatisticsStorageImpl.java
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

package com.iamnota.SpellingPractice.Student.jdk13;

import com.iamnota.SpellingPractice.Student.StatisticsStorage;
import com.iamnota.SpellingPractice.Student.StatisticsModel;
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

public class StatisticsStorageImpl
    implements StatisticsStorage
{
    protected static final String STATISTICS = "statistics.txt";

    protected static final String PROPERTIES_HEADER = "Statistics - Student - Spelling Practice";
    protected static final String PROPERTY_SUFFIX_CORRECT_COUNT = ".correct";
    protected static final String PROPERTY_SUFFIX_INCORRECT_STREAK_COUNT = ".incorrect.streak";

    private Controller controller;
    private StatisticsModel statisticsModel;

    public StatisticsStorageImpl(Controller controller, StatisticsModel statisticsModel)
    {
	this.controller = controller;
	this.statisticsModel = statisticsModel;
    }

    protected Controller getController()
    {
	return this.controller;
    }

    protected StatisticsModel getStatisticsModel()
    {
	return this.statisticsModel;
    }

    protected File getStudentStatisticsFile(String studentId)
    {
	PreferenceModel preferenceModel = this.controller.getPreferenceModel();
	File directory = new File(preferenceModel.getStudentStorageDirectory());

	File studentDirectory = new File(directory.getPath() + File.separator + studentId);
	File studentStatisticsFile = new File(studentDirectory.getPath() + File.separator + STATISTICS);

	return studentStatisticsFile;
    }

    public void load(String studentId)
	throws IOException
    {
	this.controller.getLogView().debug("StatisticsStorageImpl.load()");

	this.statisticsModel.clear();
	this.statisticsModel.setStudentId(studentId);

	FileInputStream fis = new FileInputStream(getStudentStatisticsFile(studentId));

	Properties properties = new Properties();
	properties.load(fis);

	for (Enumeration propertyNames = properties.propertyNames(); propertyNames.hasMoreElements();)
	{
	    String propertyName = (String) propertyNames.nextElement();

	    int wordIdDelimiter = -1;

	    wordIdDelimiter = propertyName.indexOf(PROPERTY_SUFFIX_CORRECT_COUNT);
	    if (wordIdDelimiter != -1)
	    {
		String wordId = propertyName.substring(0, wordIdDelimiter);

		try
		{
		    int correctCount = Integer.parseInt(properties.getProperty(propertyName, Integer.toString(0)));
		    //		    this.controller.getLogView().debug(wordId + ": correctCount = " + correctCount);
		    this.statisticsModel.setCorrectCount(wordId, correctCount);
		}

		catch (NumberFormatException e)
		{
		    this.controller.getLogView().debug("Couldn't parse " + propertyName + " \"" + properties.getProperty(propertyName) + "\"");
		    e.printStackTrace(this.controller.getLogView().getDebugStream());
		}
	    }

	    wordIdDelimiter = propertyName.indexOf(PROPERTY_SUFFIX_INCORRECT_STREAK_COUNT);
	    if (wordIdDelimiter != -1)
	    {
		String wordId = propertyName.substring(0, wordIdDelimiter);

		try
		{
		    int incorrectStreakCount = Integer.parseInt(properties.getProperty(propertyName, Integer.toString(0)));
		    //		    this.controller.getLogView().debug(wordId + ": incorrectStreakCount = " + incorrectStreakCount);
		    this.statisticsModel.setIncorrectStreakCount(wordId, incorrectStreakCount);
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
	this.controller.getLogView().debug("StatisticsStorageImpl.save()");

	FileOutputStream fos = new FileOutputStream(getStudentStatisticsFile(this.statisticsModel.getStudentId()));
	Properties properties = new Properties();

	Set wordIds = this.statisticsModel.getWordIds();
	for (Iterator iterator = wordIds.iterator(); iterator.hasNext();)
	{
	    String wordId = (String) iterator.next();
	    int correctCount = this.statisticsModel.getCorrectCount(wordId);
	    int incorrectStreakCount = this.statisticsModel.getIncorrectStreakCount(wordId);

	    properties.setProperty(wordId + PROPERTY_SUFFIX_CORRECT_COUNT, Integer.toString(correctCount));
	    properties.setProperty(wordId + PROPERTY_SUFFIX_INCORRECT_STREAK_COUNT, Integer.toString(incorrectStreakCount));
	}

	properties.store(fos, PROPERTIES_HEADER);
    }
}
