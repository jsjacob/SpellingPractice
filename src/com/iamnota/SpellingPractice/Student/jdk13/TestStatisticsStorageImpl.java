/*
 * TestStatisticsStorageImpl.java
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

import com.iamnota.SpellingPractice.Student.TestStatisticsStorage;
import com.iamnota.SpellingPractice.Student.TestStatisticsModel;
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

public class TestStatisticsStorageImpl
    implements TestStatisticsStorage
{
    protected static final String TEST_STATISTICS = "test_statistics.txt";

    protected static final String PROPERTIES_HEADER = "Test Statistics - Student - Spelling Practice";
    protected static final String PROPERTY_TEST_PREFIX = "test.";
    protected static final String PROPERTY_TEST_SUFFIX_RESULT = ".result";

    private Controller controller;
    private TestStatisticsModel testStatisticsModel;

    public TestStatisticsStorageImpl(Controller controller, TestStatisticsModel testStatisticsModel)
    {
	this.controller = controller;
	this.testStatisticsModel = testStatisticsModel;
    }

    protected Controller getController()
    {
	return this.controller;
    }

    protected TestStatisticsModel getTestStatisticsModel()
    {
	return this.testStatisticsModel;
    }

    protected File getStudentTestStatisticsFile(String studentId)
    {
	PreferenceModel preferenceModel = this.controller.getPreferenceModel();
	File directory = new File(preferenceModel.getStudentStorageDirectory());

	File studentDirectory = new File(directory.getPath() + File.separator + studentId);
	File studentTestStatisticsFile = new File(studentDirectory.getPath() + File.separator + TEST_STATISTICS);

	return studentTestStatisticsFile;
    }

    public void load(String studentId)
	throws IOException
    {
	this.controller.getLogView().debug("TestStatisticsStorageImpl.load()");

	this.testStatisticsModel.clear();
	this.testStatisticsModel.setStudentId(studentId);

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

		Boolean isCorrectBool = Boolean.valueOf(properties.getProperty(propertyName, new Boolean(false).toString()));
		//		this.controller.getLogView().debug(wordId + ": isCorrect = " + isCorrectBool);
		this.testStatisticsModel.setResult(wordId, isCorrectBool.booleanValue());
	    }
	}
    }

    public void save()
	throws IOException
    {
	this.controller.getLogView().debug("TestStatisticsStorageImpl.save()");

	FileOutputStream fos = new FileOutputStream(getStudentTestStatisticsFile(this.testStatisticsModel.getStudentId()));
	Properties properties = new Properties();

	Set wordIds = this.testStatisticsModel.getWordIds();
	for (Iterator iterator = wordIds.iterator(); iterator.hasNext();)
	{
	    String wordId = (String) iterator.next();
	    boolean isCorrect = this.testStatisticsModel.getResult(wordId);

	    properties.setProperty(wordId + PROPERTY_TEST_SUFFIX_RESULT, new Boolean(isCorrect).toString());
	}

	properties.store(fos, PROPERTIES_HEADER);
    }
}
