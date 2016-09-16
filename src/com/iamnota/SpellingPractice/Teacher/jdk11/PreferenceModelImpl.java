/*
 * PreferenceModelImpl.java
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

import com.iamnota.SpellingPractice.Teacher.PreferenceModel;

public class PreferenceModelImpl
    implements PreferenceModel
{
    //
    // Initialize preferences with defaults.
    //

    private float fontSize = 18F;
    private String viewClass = "com.iamnota.SpellingPractice.Teacher.jdk13.ViewImpl";
    private String logLevel = "warning";
    private String logViewClass = "com.iamnota.SpellingPractice.Teacher.jdk13.LogViewImpl";
    private String password = null;
    private String preferenceModelClass = "com.iamnota.SpellingPractice.Teacher.jdk11.PreferenceModelImpl";
    private String preferenceStorageClass = "com.iamnota.SpellingPractice.Teacher.jdk11.PreferenceStorageImpl";
    private String preferenceViewClass = "com.iamnota.SpellingPractice.Teacher.jdk13.PreferenceViewImpl";
    private String wordViewClass = "com.iamnota.SpellingPractice.Teacher.jdk13.WordViewImpl";
    private String wordAudioClass = "com.iamnota.SpellingPractice.Teacher.jdk13.WordAudioImpl";
    private int wordAudioMaxDurationInSeconds = 20;
    private String wordModelClass = "com.iamnota.SpellingPractice.Teacher.jdk13.WordModelImpl";
    private String wordStorageClass = "com.iamnota.SpellingPractice.Teacher.jdk13.WordStorageImpl";
    private String wordStorageDirectory = "words";
    private String studentViewClass = "com.iamnota.SpellingPractice.Teacher.jdk13.StudentViewImpl";
    private String studentModelClass = "com.iamnota.SpellingPractice.Teacher.jdk11.StudentModelImpl";
    private String studentStorageClass = "com.iamnota.SpellingPractice.Teacher.jdk11.StudentStorageImpl";
    private String studentStorageDirectory = "students";
    private String testStatisticsStorageClass = "com.iamnota.SpellingPractice.Teacher.jdk13.TestStatisticsStorageImpl";
    private String directedStatisticsStorageClass = "com.iamnota.SpellingPractice.Teacher.jdk13.DirectedStatisticsStorageImpl";
    private String studentAutoLoginId = null;
    private int mode = MODE_RANDOM;
    private boolean isShowTestingStatistics = false;
    private boolean isShowDirectedStatistics = true;

    public PreferenceModelImpl()
    {
    }

    public float getFontSize()
    {
	return this.fontSize;
    }

    public void setFontSize(float fontSize)
    {
	this.fontSize = fontSize;
    }

    public String getViewClass()
    {
	return this.viewClass;
    }

    public void setViewClass(String viewClass)
    {
	this.viewClass = viewClass;
    }

    public String getLogLevel()
    {
	return this.logLevel;
    }

    public void setLogLevel(String logLevel)
    {
	this.logLevel = logLevel;
    }

    public String getLogViewClass()
    {
	return this.logViewClass;
    }

    public void setLogViewClass(String logViewClass)
    {
	this.logViewClass = logViewClass;
    }

    public String getPassword()
    {
	return this.password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    public String getPreferenceModelClass()
    {
	return preferenceModelClass;
    }

    public void setPreferenceModelClass(String preferenceModelClass)
    {
	this.preferenceModelClass = preferenceModelClass;
    }

    public String getPreferenceStorageClass()
    {
	return preferenceStorageClass;
    }

    public void setPreferenceStorageClass(String preferenceStorageClass)
    {
	this.preferenceStorageClass = preferenceStorageClass;
    }

    public String getPreferenceViewClass()
    {
	return preferenceViewClass;
    }

    public void setPreferenceViewClass(String preferenceViewClass)
    {
	this.preferenceViewClass = preferenceViewClass;
    }

    public String getWordViewClass()
    {
	return this.wordViewClass;
    }

    public void setWordViewClass(String wordViewClass)
    {
	this.wordViewClass = wordViewClass;
    }

    public String getWordAudioClass()
    {
	return this.wordAudioClass;
    }

    public void setWordAudioClass(String wordAudioClass)
    {
	this.wordAudioClass = wordAudioClass;
    }

    public int getWordAudioMaxDurationInSeconds()
    {
	return this.wordAudioMaxDurationInSeconds;
    }

    public void setWordAudioMaxDurationInSeconds(int maxDurationInSeconds)
    {
	this.wordAudioMaxDurationInSeconds = maxDurationInSeconds;
    }

    public String getWordModelClass()
    {
	return this.wordModelClass;
    }

    public void setWordModelClass(String wordModelClass)
    {
	this.wordModelClass = wordModelClass;
    }

    public String getWordStorageClass()
    {
	return this.wordStorageClass;
    }

    public void setWordStorageClass(String wordStorageClass)
    {
	this.wordStorageClass = wordStorageClass;
    }

    public String getWordStorageDirectory()
    {
	return this.wordStorageDirectory;
    }

    public void setWordStorageDirectory(String wordStorageDirectory)
    {
	this.wordStorageDirectory = wordStorageDirectory;
    }

    public String getStudentViewClass()
    {
	return this.studentViewClass;
    }

    public void setStudentViewClass(String studentViewClass)
    {
	this.studentViewClass = studentViewClass;
    }

    public String getStudentModelClass()
    {
	return this.studentModelClass;
    }

    public void setStudentModelClass(String studentModelClass)
    {
	this.studentModelClass = studentModelClass;
    }

    public String getStudentStorageClass()
    {
	return this.studentStorageClass;
    }

    public void setStudentStorageClass(String studentStorageClass)
    {
	this.studentStorageClass = studentStorageClass;
    }

    public String getStudentStorageDirectory()
    {
	return this.studentStorageDirectory;
    }

    public void setStudentStorageDirectory(String studentStorageDirectory)
    {
	this.studentStorageDirectory = studentStorageDirectory;
    }

    public String getTestStatisticsStorageClass()
    {
	return this.testStatisticsStorageClass;
    }

    public void setTestStatisticsStorageClass(String testStatisticsStorageClass)
    {
	this.testStatisticsStorageClass = testStatisticsStorageClass;
    }

    public String getDirectedStatisticsStorageClass()
    {
	return this.directedStatisticsStorageClass;
    }

    public void setDirectedStatisticsStorageClass(String directedStatisticsStorageClass)
    {
	this.directedStatisticsStorageClass = directedStatisticsStorageClass;
    }

    public String getStudentAutoLoginId()
    {
	return this.studentAutoLoginId;
    }

    public void setStudentAutoLoginId(String studentId)
    {
	this.studentAutoLoginId = studentId;
    }

    public int getMode()
    {
	return this.mode;
    }

    public void setMode(int mode)
    {
	this.mode = mode;
    }

    public void setShowTestingStatistics()
    {
	this.isShowDirectedStatistics = false;
	this.isShowTestingStatistics = true;
    }

    public boolean getIsShowTestingStatistics()
    {
	return this.isShowTestingStatistics;
    }

    public void setShowDirectedStatistics()
    {
	this.isShowTestingStatistics = false;
	this.isShowDirectedStatistics = true;
    }

    public boolean getIsShowDirectedStatistics()
    {
	return this.isShowDirectedStatistics;
    }
}
