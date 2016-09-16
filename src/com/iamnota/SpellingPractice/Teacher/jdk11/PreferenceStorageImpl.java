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

package com.iamnota.SpellingPractice.Teacher.jdk11;

import com.iamnota.SpellingPractice.Teacher.PreferenceStorage;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;
import com.iamnota.SpellingPractice.Teacher.PreferenceModel;

import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferenceStorageImpl
    implements PreferenceStorage
{
    protected static final String PROPERTIES_HEADER = "Teacher - Spelling Practice";

    protected static final String PROPERTY_FONT_SIZE = "Teacher.font.size";
    protected static final String PROPERTY_VIEW = "Teacher.View";
    protected static final String PROPERTY_LOG_LEVEL = "Teacher.Log.level";
    protected static final String PROPERTY_LOG_VIEW = "Teacher.Log.View";
    protected static final String PROPERTY_PASSWORD = "Teacher.password";
    protected static final String PROPERTY_PREFERENCE_MODEL = "Teacher.Preference.Model";
    protected static final String PROPERTY_PREFERENCE_STORAGE = "Teacher.Preference.Storage";
    protected static final String PROPERTY_PREFERENCE_VIEW = "Teacher.Preference.View";
    protected static final String PROPERTY_WORD_VIEW = "Teacher.Word.View";
    protected static final String PROPERTY_WORD_AUDIO = "Teacher.Word.Audio";
    protected static final String PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS = "Teacher.Word.Audio.maxDurationInSeconds";
    protected static final String PROPERTY_WORD_MODEL = "Teacher.Word.Model";
    protected static final String PROPERTY_WORD_STORAGE = "Teacher.Word.Storage";
    protected static final String PROPERTY_WORD_STORAGE_DIRECTORY = "Teacher.Word.Storage.directory";
    protected static final String PROPERTY_STUDENT_VIEW = "Teacher.Student.View";
    protected static final String PROPERTY_STUDENT_MODEL = "Teacher.Student.Model";
    protected static final String PROPERTY_STUDENT_STORAGE = "Teacher.Student.Storage";
    protected static final String PROPERTY_STUDENT_STORAGE_DIRECTORY = "Teacher.Student.Storage.directory";
    protected static final String PROPERTY_TEST_STATISTICS_STORAGE = "Teacher.TestStatistics.Storage";
    protected static final String PROPERTY_DIRECTED_STATISTICS_STORAGE = "Teacher.DirectedStatistics.Storage";

    protected static final String AUTO_LOGIN_FILENAME = "autologin.txt";
    protected static final String MODE_FILENAME = "mode.txt";


    private PreferenceController preference;
    private PreferenceModel preferenceModel;

    private File preferenceFile;

    public PreferenceStorageImpl(PreferenceController preference, PreferenceModel preferenceModel, File preferenceFile)
    {
	this.preference = preference;
	this.preferenceModel = preferenceModel;
	this.preferenceFile = preferenceFile;
    }

    protected PreferenceController getPreferenceController()
    {
	return this.preference;
    }

    protected PreferenceModel getPreferenceModel()
    {
	return this.preferenceModel;
    }

    protected File getPreferenceFile()
    {
	return this.preferenceFile;
    }

    public void save()
	throws IOException
    {
	this.preference.getLogView().debug("PreferenceStorageImpl.save()");

	throw new IOException("com.iamnota.SpellingPractice.Teacher.jdk11.PreferenceStorageImpl.save() not supported");
    }

    public void load()
	throws IOException
    {
	this.preference.getLogView().debug("PreferenceStorageImpl.load()");

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

	float fontSize = this.preferenceModel.getFontSize();

	try
	{
	    fontSize = Float.parseFloat(properties.getProperty(PreferenceStorageImpl.PROPERTY_FONT_SIZE, Float.toString(fontSize)));
	}

	catch (NumberFormatException e)
	{
	    this.preference.getLogView().debug("Couldn't parse " + PROPERTY_FONT_SIZE + " \"" + properties.getProperty(PROPERTY_FONT_SIZE) + "\"");
	    e.printStackTrace(this.preference.getLogView().getDebugStream());
	    this.preference.getLogView().warning("Couldn't parse " + PROPERTY_FONT_SIZE + " \"" + properties.getProperty(PROPERTY_FONT_SIZE) + "\"");
	}

	this.preferenceModel.setFontSize(fontSize);

	this.preferenceModel.setViewClass(properties.getProperty(PROPERTY_VIEW, this.preferenceModel.getViewClass()));
	this.preferenceModel.setLogLevel(properties.getProperty(PROPERTY_LOG_LEVEL, this.preferenceModel.getLogLevel()));
	this.preferenceModel.setLogViewClass(properties.getProperty(PROPERTY_LOG_VIEW, this.preferenceModel.getLogViewClass()));
	this.preferenceModel.setPassword(properties.getProperty(PROPERTY_PASSWORD, this.preferenceModel.getPassword()));
	this.preferenceModel.setPreferenceModelClass(properties.getProperty(PROPERTY_PREFERENCE_MODEL, this.preferenceModel.getPreferenceModelClass()));
	this.preferenceModel.setPreferenceStorageClass(properties.getProperty(PROPERTY_PREFERENCE_STORAGE, this.preferenceModel.getPreferenceStorageClass()));
	this.preferenceModel.setPreferenceViewClass(properties.getProperty(PROPERTY_PREFERENCE_VIEW, this.preferenceModel.getPreferenceViewClass()));
	this.preferenceModel.setWordViewClass(properties.getProperty(PROPERTY_WORD_VIEW, this.preferenceModel.getWordViewClass()));
	this.preferenceModel.setWordAudioClass(properties.getProperty(PROPERTY_WORD_AUDIO, this.preferenceModel.getWordAudioClass()));

	int wordAudioMaxDurationInSeconds = this.preferenceModel.getWordAudioMaxDurationInSeconds();

	try
	{
	    wordAudioMaxDurationInSeconds = Integer.parseInt(properties.getProperty(PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS, Integer.toString(wordAudioMaxDurationInSeconds)));
	}

	catch (NumberFormatException e)
	{
	    this.preference.getLogView().debug("Couldn't parse " + PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS + " \"" + properties.getProperty(PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS) + "\"");
	    e.printStackTrace(this.preference.getLogView().getDebugStream());
	    this.preference.getLogView().warning("Couldn't parse " + PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS + " \"" + properties.getProperty(PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS) + "\"");
	}

	this.preferenceModel.setWordAudioMaxDurationInSeconds(wordAudioMaxDurationInSeconds);

	this.preferenceModel.setWordModelClass(properties.getProperty(PROPERTY_WORD_MODEL, this.preferenceModel.getWordModelClass()));
	this.preferenceModel.setWordStorageClass(properties.getProperty(PROPERTY_WORD_STORAGE, this.preferenceModel.getWordStorageClass()));
	this.preferenceModel.setWordStorageDirectory(properties.getProperty(PROPERTY_WORD_STORAGE_DIRECTORY, this.preferenceModel.getWordStorageDirectory()));
	this.preferenceModel.setStudentViewClass(properties.getProperty(PROPERTY_STUDENT_VIEW, this.preferenceModel.getStudentViewClass()));
	this.preferenceModel.setStudentModelClass(properties.getProperty(PROPERTY_STUDENT_MODEL, this.preferenceModel.getStudentModelClass()));
	this.preferenceModel.setStudentStorageClass(properties.getProperty(PROPERTY_STUDENT_STORAGE, this.preferenceModel.getStudentStorageClass()));
	this.preferenceModel.setStudentStorageDirectory(properties.getProperty(PROPERTY_STUDENT_STORAGE_DIRECTORY, this.preferenceModel.getStudentStorageDirectory()));
	this.preferenceModel.setTestStatisticsStorageClass(properties.getProperty(PROPERTY_TEST_STATISTICS_STORAGE, this.preferenceModel.getTestStatisticsStorageClass()));
	this.preferenceModel.setDirectedStatisticsStorageClass(properties.getProperty(PROPERTY_DIRECTED_STATISTICS_STORAGE, this.preferenceModel.getDirectedStatisticsStorageClass()));
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
		    this.preference.getLogView().debug("Couldn't parse file " + MODE_FILENAME);
		    e.printStackTrace(this.preference.getLogView().getDebugStream());
		    this.preference.getLogView().warning("Couldn't parse file " + MODE_FILENAME);
		}
		
		this.preferenceModel.setMode(mode);
	    }
	}
    }
}
