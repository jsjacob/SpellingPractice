/*
 * TestStatisticsStorageImpl.java
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

import com.iamnota.SpellingPractice.Teacher.TestStatisticsStorage;
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

public class TestStatisticsStorageImpl
    implements TestStatisticsStorage
{
    protected static final String TEST_STATISTICS = "test_statistics.txt";

    protected static final String PROPERTY_TEST_PREFIX = "test.";
    protected static final String PROPERTY_TEST_SUFFIX_RESULT = ".result";

    private StudentController studentController;
    private StudentModel studentModel;

    public TestStatisticsStorageImpl(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;
    }

    protected File getStudentTestStatisticsFile(String studentId)
    {
	PreferenceController preference = this.studentController.getPreferenceController();
	File directory = new File(preference.getStudentStorageDirectory());

	File studentDirectory = new File(directory.getPath() + File.separator + studentId);
	File studentTestStatisticsFile = new File(studentDirectory.getPath() + File.separator + TEST_STATISTICS);

	return studentTestStatisticsFile;
    }

    public void load()
    {
	this.studentController.getLogView().debug("TestStatisticsStorageImpl.load()");

	WordController wordController = this.studentController.getWordController();
	Map idsToWords = wordController.getWordStorage().getIdsToWords();

	Map correctIdsToWords = new HashMap();
	Map incorrectIdsToWords = new HashMap();

	try
	{
	    String studentId = this.studentModel.getId();
	    FileInputStream fis = new FileInputStream(getStudentTestStatisticsFile(studentId));
	    
	    Properties properties = new Properties();
	    properties.load(fis);
	    
	    for (Enumeration propertyNames = properties.propertyNames(); propertyNames.hasMoreElements();)
	    {
		    String propertyName = (String) propertyNames.nextElement();
		    
		    int wordIdDelimiter = -1;
		    
		    wordIdDelimiter = propertyName.indexOf(PROPERTY_TEST_SUFFIX_RESULT);
		    if (wordIdDelimiter != -1)
		    {
			String wordId = propertyName.substring(0, wordIdDelimiter);
			String word = (String) idsToWords.get(wordId);
			
			Boolean isCorrectBool = Boolean.valueOf(properties.getProperty(propertyName, new Boolean(false).toString()));
			//		this.studentController.getLogView().debug(wordId + ": isCorrect = " + isCorrectBool);
			if (isCorrectBool.booleanValue())
			{
			    correctIdsToWords.put(wordId, word);
			}
			else
			{
			    incorrectIdsToWords.put(wordId, word);
			}
		    }
	    }
	}

	catch (IOException e)
	{
	    // Ignore IO exceptions (e.g., file not found) for test statistics:
	    // Student may not have run SpellingPractice yet.
	}

	this.studentModel.setCorrectIdsToWords(correctIdsToWords);
	this.studentModel.setIncorrectIdsToWords(incorrectIdsToWords);
    }

    public void delete()
	throws IOException
    {
	this.studentController.getLogView().debug("TestStatisticsStorageImpl.delete()");

	String id = this.studentModel.getId();
	if (id != null)
	{
	    getStudentTestStatisticsFile(id).delete();
	}
    }
}
