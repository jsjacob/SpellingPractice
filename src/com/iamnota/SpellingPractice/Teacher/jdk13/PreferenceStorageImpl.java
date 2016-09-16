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

package com.iamnota.SpellingPractice.Teacher.jdk13;

import com.iamnota.SpellingPractice.Teacher.PreferenceStorage;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;
import com.iamnota.SpellingPractice.Teacher.PreferenceModel;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PreferenceStorageImpl
    extends com.iamnota.SpellingPractice.Teacher.jdk11.PreferenceStorageImpl
{
    public PreferenceStorageImpl(PreferenceController preference, PreferenceModel preferenceModel, File preferenceFile)
    {
	super(preference, preferenceModel, preferenceFile);
    }

    public void save()
	throws IOException
    {
	getPreferenceController().getLogView().debug("PreferenceStorageImpl.save()");

	savePreferenceFile();
	saveStudentAutoLoginFile();
	saveModeFile();
    }

    protected void savePreferenceFile()
	throws IOException
    {
	FileOutputStream fos = new FileOutputStream(getPreferenceFile());
	Properties properties = new Properties();

	properties.setProperty(PROPERTY_FONT_SIZE, Float.toString(getPreferenceModel().getFontSize()));
	properties.setProperty(PROPERTY_VIEW, getPreferenceModel().getViewClass());
	properties.setProperty(PROPERTY_LOG_LEVEL, getPreferenceModel().getLogLevel());
	properties.setProperty(PROPERTY_LOG_VIEW, getPreferenceModel().getLogViewClass());
	if (getPreferenceModel().getPassword() != null) { properties.setProperty(PROPERTY_PASSWORD, getPreferenceModel().getPassword()); }
	properties.setProperty(PROPERTY_PREFERENCE_MODEL, getPreferenceModel().getPreferenceModelClass());
	properties.setProperty(PROPERTY_PREFERENCE_STORAGE, getPreferenceModel().getPreferenceStorageClass());
	properties.setProperty(PROPERTY_PREFERENCE_VIEW, getPreferenceModel().getPreferenceViewClass());
	properties.setProperty(PROPERTY_WORD_VIEW, getPreferenceModel().getWordViewClass());
	properties.setProperty(PROPERTY_WORD_AUDIO, getPreferenceModel().getWordAudioClass());
	properties.setProperty(PROPERTY_WORD_AUDIO_MAX_DURATION_IN_SECONDS, Integer.toString(getPreferenceModel().getWordAudioMaxDurationInSeconds()));
	properties.setProperty(PROPERTY_WORD_MODEL, getPreferenceModel().getWordModelClass());
	properties.setProperty(PROPERTY_WORD_STORAGE, getPreferenceModel().getWordStorageClass());
	properties.setProperty(PROPERTY_WORD_STORAGE_DIRECTORY, getPreferenceModel().getWordStorageDirectory());
	properties.setProperty(PROPERTY_STUDENT_VIEW, getPreferenceModel().getStudentViewClass());
	properties.setProperty(PROPERTY_STUDENT_MODEL, getPreferenceModel().getStudentModelClass());
	properties.setProperty(PROPERTY_STUDENT_STORAGE, getPreferenceModel().getStudentStorageClass());
	properties.setProperty(PROPERTY_STUDENT_STORAGE_DIRECTORY, getPreferenceModel().getStudentStorageDirectory());
	properties.setProperty(PROPERTY_TEST_STATISTICS_STORAGE, getPreferenceModel().getTestStatisticsStorageClass());
	properties.setProperty(PROPERTY_DIRECTED_STATISTICS_STORAGE, getPreferenceModel().getDirectedStatisticsStorageClass());

	properties.store(fos, PROPERTIES_HEADER);
    }

    protected void saveStudentAutoLoginFile()
	throws IOException
    {
	File directory = new File(getPreferenceModel().getStudentStorageDirectory());

	String studentAutoLoginId = getPreferenceModel().getStudentAutoLoginId();

	FileWriter studentAutoLoginIdFw = new FileWriter(directory.getPath() + File.separator + AUTO_LOGIN_FILENAME);
	if (studentAutoLoginId != null)
	{
	    studentAutoLoginIdFw.write(studentAutoLoginId);
	}
	studentAutoLoginIdFw.flush();
	studentAutoLoginIdFw.close();
    }

    protected void saveModeFile()
	throws IOException
    {
	File directory = new File(getPreferenceModel().getWordStorageDirectory());

	String modeString = Integer.toString(getPreferenceModel().getMode());

	FileWriter modeFw = new FileWriter(directory.getPath() + File.separator + MODE_FILENAME);
	if (modeString != null)
	{
	    modeFw.write(modeString);
	}
	modeFw.flush();
	modeFw.close();
    }
}
