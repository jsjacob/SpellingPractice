/*
 * PreferenceStorageImpl.java
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

package com.iamnota.SpellingPractice.Student.jdk11;

import com.iamnota.SpellingPractice.Student.PreferenceStorage;
import com.iamnota.SpellingPractice.Student.PreferenceModel;
import com.iamnota.SpellingPractice.Student.LogView;

import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferenceStorageImpl
    implements PreferenceStorage
{
    protected static final String PROPERTY_PREFERENCE_MODEL = "Student.Preference.Model";
    protected static final String PROPERTY_PREFERENCE_STORAGE = "Student.Preference.Storage";
    protected static final String PROPERTY_VIEW_CLASS = "Student.View";
    protected static final String PROPERTY_LOG_LEVEL = "Student.Log.level";
    protected static final String PROPERTY_LOG_VIEW = "Student.Log.View";
    protected static final String PROPERTY_WORD_AUDIO = "Student.Word.Audio";
    protected static final String PROPERTY_WORD_MODEL = "Student.Word.Model";
    protected static final String PROPERTY_WORD_STORAGE = "Student.Word.Storage";
    protected static final String PROPERTY_WORD_STORAGE_DIRECTORY = "Student.Word.Storage.directory";
    protected static final String PROPERTY_STUDENT_MODEL = "Student.Student.Model";
    protected static final String PROPERTY_STUDENT_STORAGE = "Student.Student.Storage";
    protected static final String PROPERTY_STUDENT_STORAGE_DIRECTORY = "Student.Student.Storage.directory";
    protected static final String PROPERTY_DIRECTED_STATISTICS_MODEL = "Student.DirectedStatistics.Model";
    protected static final String PROPERTY_DIRECTED_STATISTICS_STORAGE = "Student.DirectedStatistics.Storage";
    protected static final String PROPERTY_DIRECTED_STATISTICS = "Student.DirectedStatistics";
    protected static final String PROPERTY_TEST_STATISTICS_MODEL = "Student.TestStatistics.Model";
    protected static final String PROPERTY_TEST_STATISTICS_STORAGE = "Student.TestStatistics.Storage";
    protected static final String PROPERTY_TEST_STATISTICS = "Student.TestStatistics";
    protected static final String PROPERTY_FONT_SIZE = "Student.font.size";
    protected static final String PROPERTY_LOGIN_MODEL = "Student.Login.Model";
    protected static final String PROPERTY_LOGIN_VIEW = "Student.Login.View";
    protected static final String PROPERTY_SPELLING_MODEL = "Student.Spelling.Model";
    protected static final String PROPERTY_SPELLING_VIEW = "Student.Spelling.View";

    protected static final String AUTO_LOGIN_FILENAME = "autologin.txt";
    protected static final String MODE_FILENAME = "mode.txt";


    private LogView logView;

    private PreferenceModel preferenceModel;

    private File preferenceFile;

    public PreferenceStorageImpl(LogView logView, PreferenceModel preferenceModel, File preferenceFile)
    {
	this.logView = logView;
	this.preferenceModel = preferenceModel;
	this.preferenceFile = preferenceFile;
    }

    protected PreferenceModel getPreferenceModel()
    {
	return this.preferenceModel;
    }

    protected File getPreferenceFile()
    {
	return this.preferenceFile;
    }

    public void load()
	throws IOException
    {
	this.logView.debug("PreferenceStorageImpl.load()");

	loadPreferenceFile();
	loadStudentAutoLoginFile();
	loadModeFile();
    }

    protected void loadPreferenceFile()
	throws IOException
    {
	FileInputStream fis = new FileInputStream(this.preferenceFile);

	Properties properties = new Properties();
	properties.load(fis);

	this.preferenceModel.setPreferenceModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_PREFERENCE_MODEL, this.preferenceModel.getPreferenceModelClass()));
	this.preferenceModel.setPreferenceStorageClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_PREFERENCE_STORAGE, this.preferenceModel.getPreferenceStorageClass()));
	this.preferenceModel.setViewClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_VIEW_CLASS, this.preferenceModel.getViewClass()));
	this.preferenceModel.setLogLevel(properties.getProperty(PreferenceStorageImpl.PROPERTY_LOG_LEVEL, this.preferenceModel.getLogLevel()));
	this.preferenceModel.setLogViewClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_LOG_VIEW, this.preferenceModel.getLogViewClass()));
	this.preferenceModel.setWordAudioClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_WORD_AUDIO, this.preferenceModel.getWordAudioClass()));
	this.preferenceModel.setWordModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_WORD_MODEL, this.preferenceModel.getWordModelClass()));
	this.preferenceModel.setWordStorageClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_WORD_STORAGE, this.preferenceModel.getWordStorageClass()));
	this.preferenceModel.setWordStorageDirectory(properties.getProperty(PreferenceStorageImpl.PROPERTY_WORD_STORAGE_DIRECTORY, this.preferenceModel.getWordStorageDirectory()));
	this.preferenceModel.setStudentModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_STUDENT_MODEL, this.preferenceModel.getStudentModelClass()));
	this.preferenceModel.setStudentStorageClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_STUDENT_STORAGE, this.preferenceModel.getStudentStorageClass()));
	this.preferenceModel.setStudentStorageDirectory(properties.getProperty(PreferenceStorageImpl.PROPERTY_STUDENT_STORAGE_DIRECTORY, this.preferenceModel.getStudentStorageDirectory()));

	this.preferenceModel.setDirectedStatisticsModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_DIRECTED_STATISTICS_MODEL, this.preferenceModel.getDirectedStatisticsModelClass()));
	this.preferenceModel.setDirectedStatisticsStorageClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_DIRECTED_STATISTICS_STORAGE, this.preferenceModel.getDirectedStatisticsStorageClass()));

	this.preferenceModel.setTestStatisticsModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_TEST_STATISTICS_MODEL, this.preferenceModel.getTestStatisticsModelClass()));
	this.preferenceModel.setTestStatisticsStorageClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_TEST_STATISTICS_STORAGE, this.preferenceModel.getTestStatisticsStorageClass()));


	float fontSize = this.preferenceModel.getFontSize();

	try
	{
	    fontSize = Float.parseFloat(properties.getProperty(PreferenceStorageImpl.PROPERTY_FONT_SIZE, Float.toString(fontSize)));
	}

	catch (NumberFormatException e)
	{
	    this.logView.debug("Couldn't parse " + PreferenceStorageImpl.PROPERTY_FONT_SIZE + " \"" + properties.getProperty(PreferenceStorageImpl.PROPERTY_FONT_SIZE) + "\"");
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.warning("Couldn't parse " + PreferenceStorageImpl.PROPERTY_FONT_SIZE + " \"" + properties.getProperty(PreferenceStorageImpl.PROPERTY_FONT_SIZE) + "\"");
	}

	this.preferenceModel.setFontSize(fontSize);

	this.preferenceModel.setLoginModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_LOGIN_MODEL, this.preferenceModel.getLoginModelClass()));
	this.preferenceModel.setLoginViewClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_LOGIN_VIEW, this.preferenceModel.getLoginViewClass()));
	this.preferenceModel.setSpellingModelClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_SPELLING_MODEL, this.preferenceModel.getSpellingModelClass()));
	this.preferenceModel.setSpellingViewClass(properties.getProperty(PreferenceStorageImpl.PROPERTY_SPELLING_VIEW, this.preferenceModel.getSpellingViewClass()));
    }

    protected void loadStudentAutoLoginFile()
	throws IOException
    {
	File directory = new File(this.preferenceModel.getStudentStorageDirectory());

	File autoLoginFile = new File(directory.getPath() + File.separator + AUTO_LOGIN_FILENAME);
	if (autoLoginFile.exists())
	{
	    FileReader autoLoginFr = new FileReader(autoLoginFile);
	    StringBuffer autoLoginSb = new StringBuffer();
	    int bufferLength = 256;
	    char[] buffer = new char[bufferLength];
	    for (int charsRead = autoLoginFr.read(buffer); charsRead != -1; charsRead = autoLoginFr.read(buffer))
	    {
		autoLoginSb.append(buffer, 0, charsRead);
	    }

	    String studentAutoLoginId = autoLoginSb.toString();
	    if ((studentAutoLoginId != null) && (studentAutoLoginId.length() > 0))
	    {
		this.preferenceModel.setStudentAutoLoginId(studentAutoLoginId);
	    }
	}
    }

    protected void loadModeFile()
	throws IOException
    {
	File directory = new File(this.preferenceModel.getWordStorageDirectory());

	File modeFile = new File(directory.getPath() + File.separator + MODE_FILENAME);
	if (modeFile.exists())
	{
	    FileReader modeFr = new FileReader(modeFile);
	    StringBuffer modeSb = new StringBuffer();
	    int bufferLength = 256;
	    char[] buffer = new char[bufferLength];
	    for (int charsRead = modeFr.read(buffer); charsRead != -1; charsRead = modeFr.read(buffer))
	    {
		modeSb.append(buffer, 0, charsRead);
	    }

	    String modeString = modeSb.toString();
	    if ((modeString != null) && (modeString.length() > 0))
	    {
		int mode = this.preferenceModel.getMode();

		try
		{
		    mode = Integer.parseInt(modeString);
		}

		catch (NumberFormatException e)
		{
		    this.logView.debug("Couldn't parse file " + MODE_FILENAME);
		    e.printStackTrace(this.logView.getDebugStream());
		    this.logView.warning("Couldn't parse file " + MODE_FILENAME);
		}
		
		this.preferenceModel.setMode(mode);
	    }
	}
    }
}
