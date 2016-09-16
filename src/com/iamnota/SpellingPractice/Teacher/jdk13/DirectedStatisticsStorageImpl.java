/*
 * DirectedStatisticsStorageImpl.java
 *
 * Copyright (C) 2004 John S. Jacob <jsjacob@iamnota.com>
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

package com.iamnota.SpellingPractice.Teacher.jdk13;

import com.iamnota.SpellingPractice.Teacher.DirectedStatisticsStorage;
import com.iamnota.SpellingPractice.Teacher.StudentModel;
import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;
import com.iamnota.SpellingPractice.Teacher.WordController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class DirectedStatisticsStorageImpl
    implements DirectedStatisticsStorage
{
    protected static final String DIRECTED_STATISTICS = "directed_statistics.txt";

    protected static final String PROPERTY_DIRECTED_PREFIX = "directed.";
    protected static final String PROPERTY_DIRECTED_SUFFIX_CORRECT_COUNT = ".correct";
    protected static final String PROPERTY_DIRECTED_SUFFIX_INCORRECT_STREAK_COUNT = ".incorrect.streak";

    private StudentController studentController;
    private StudentModel studentModel;

    protected static class Counts
    {
	public int correctCount;
	public int incorrectStreakCount;
    }

    public DirectedStatisticsStorageImpl(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;
    }

    protected File getStudentDirectedStatisticsFile(String studentId)
    {
	PreferenceController preference = this.studentController.getPreferenceController();
	File directory = new File(preference.getStudentStorageDirectory());

	File studentDirectory = new File(directory.getPath() + File.separator + studentId);
	File studentDirectedStatisticsFile = new File(studentDirectory.getPath() + File.separator + DIRECTED_STATISTICS);

	return studentDirectedStatisticsFile;
    }

    public void load()
    {
	this.studentController.getLogView().debug("DirectedStatisticsStorageImpl.load()");

	WordController wordController = this.studentController.getWordController();
	Map idsToWords = wordController.getWordStorage().getIdsToWords();

	Map idsToCounts = new HashMap();

	try
	{
	    String studentId = this.studentModel.getId();
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
			//		    this.studentController.getLogView().debug(wordId + ": correctCount = " + correctCount);

			Counts counts = (Counts) idsToCounts.get(wordId);
			if (counts == null)
			{
			    counts = new Counts();
			    counts.correctCount = 0;
			    counts.incorrectStreakCount = 0;

			    idsToCounts.put(wordId, counts);
			}

			counts.correctCount = correctCount;
		    }

		    catch (NumberFormatException e)
		    {
			this.studentController.getLogView().debug("Couldn't parse " + propertyName + " \"" + properties.getProperty(propertyName) + "\"");
			e.printStackTrace(this.studentController.getLogView().getDebugStream());
		    }
		}
		
		wordIdDelimiter = propertyName.indexOf(PROPERTY_DIRECTED_SUFFIX_INCORRECT_STREAK_COUNT);
		if (wordIdDelimiter != -1)
		{
		    String wordId = propertyName.substring(0, wordIdDelimiter);
		    String word = (String) idsToWords.get(wordId);
		    
		    try
		    {
			int incorrectStreakCount = Integer.parseInt(properties.getProperty(propertyName, Integer.toString(0)));
			//		    this.studentController.getLogView().debug(wordId + ": incorrectStreakCount = " + incorrectStreakCount);

			Counts counts = (Counts) idsToCounts.get(wordId);
			if (counts == null)
			{
			    counts = new Counts();
			    counts.correctCount = 0;
			    counts.incorrectStreakCount = 0;

			    idsToCounts.put(wordId, counts);
			}

			counts.incorrectStreakCount = incorrectStreakCount;
		    }
		    
		    catch (NumberFormatException e)
		    {
			this.studentController.getLogView().debug("Couldn't parse " + propertyName + " \"" + properties.getProperty(propertyName) + "\"");
			e.printStackTrace(this.studentController.getLogView().getDebugStream());
		    }
		}
		
	    }
	}

	catch (IOException e)
	{
	    // Ignore IO exceptions (e.g., file not found) for directed statistics:
	    // Student may not have run SpellingPractice yet.
	}

	Map correctIdsToWords = new HashMap();
	Map incorrectIdsToWords = new HashMap();

	for (Iterator iterator = idsToCounts.keySet().iterator(); iterator.hasNext(); )
	{
	    String wordId = (String) iterator.next();
	    String word = (String) idsToWords.get(wordId);
	    Counts counts = (Counts) idsToCounts.get(wordId);

	    if (counts.correctCount > 0)
	    {
		if (counts.incorrectStreakCount == 0)
		{
		    correctIdsToWords.put(wordId, word);
		}
		else
		{
		    incorrectIdsToWords.put(wordId, word);
		}
	    }
	    else
	    {
		if (counts.incorrectStreakCount == 0)
		{
		    // No data yet
		}
		else
		{
		    incorrectIdsToWords.put(wordId, word);
		}
	    }
	}

	this.studentModel.setCorrectIdsToWords(correctIdsToWords);
	this.studentModel.setIncorrectIdsToWords(incorrectIdsToWords);
    }

    public void delete()
	throws IOException
    {
	this.studentController.getLogView().debug("DirectedStatisticsStorageImpl.delete()");

	String id = this.studentModel.getId();
	if (id != null)
	{
	    getStudentDirectedStatisticsFile(id).delete();
	}
    }
}
