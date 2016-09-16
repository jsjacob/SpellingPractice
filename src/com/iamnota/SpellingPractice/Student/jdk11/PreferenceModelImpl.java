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

package com.iamnota.SpellingPractice.Student.jdk11;

import com.iamnota.SpellingPractice.Student.PreferenceModel;

public class PreferenceModelImpl
    implements PreferenceModel
{
    //
    // Initialize preferences with defaults.
    //

    private String preferenceModelClass = "com.iamnota.SpellingPractice.Student.jdk11.PreferenceModelImpl";
    private String preferenceStorageClass = "com.iamnota.SpellingPractice.Student.jdk11.PreferenceStorageImpl";
    private String viewClass = "com.iamnota.SpellingPractice.Student.jdk13.ViewImpl";
    private String logLevel = "warning";
    private String logViewClass = "com.iamnota.SpellingPractice.Student.jdk13.LogViewImpl";
    private String wordAudioClass = "com.iamnota.SpellingPractice.Student.jdk13.WordAudioImpl";
    private String wordModelClass = "com.iamnota.SpellingPractice.Student.jdk13.WordModelImpl";
    private String wordStorageClass = "com.iamnota.SpellingPractice.Student.jdk13.WordStorageImpl";
    private String wordStorageDirectory = "words";
    private String studentModelClass = "com.iamnota.SpellingPractice.Student.jdk11.StudentModelImpl";
    private String studentStorageClass = "com.iamnota.SpellingPractice.Student.jdk11.StudentStorageImpl";
    private String studentStorageDirectory = "students";
    private String studentAutoLoginId = null;
    private int mode = PreferenceModel.MODE_RANDOM;
    private String directedStatisticsModelClass = "com.iamnota.SpellingPractice.Student.jdk11.DirectedStatisticsModelImpl";
    private String directedStatisticsStorageClass = "com.iamnota.SpellingPractice.Student.jdk13.DirectedStatisticsStorageImpl";
    private boolean useTestStatistics = false;
    private String testStatisticsModelClass = "com.iamnota.SpellingPractice.Student.jdk11.TestStatisticsModelImpl";
    private String testStatisticsStorageClass = "com.iamnota.SpellingPractice.Student.jdk13.TestStatisticsStorageImpl";
    private float fontSize = 18F;
    private String loginModelClass = "com.iamnota.SpellingPractice.Student.jdk11.LoginModelImpl";
    private String loginViewClass = "com.iamnota.SpellingPractice.Student.jdk13.LoginViewImpl";
    private String spellingModelClass = "com.iamnota.SpellingPractice.Student.jdk11.SpellingModelImpl";
    private String spellingViewClass = "com.iamnota.SpellingPractice.Student.jdk13.SpellingViewImpl";


    public PreferenceModelImpl()
    {
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

    public String getViewClass()
    {
	return viewClass;
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

    public String getWordAudioClass()
    {
	return this.wordAudioClass;
    }

    public void setWordAudioClass(String wordAudioClass)
    {
	this.wordAudioClass = wordAudioClass;
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

    public String getDirectedStatisticsModelClass()
    {
	return this.directedStatisticsModelClass;
    }

    public void setDirectedStatisticsModelClass(String directedStatisticsModelClass)
    {
	this.directedStatisticsModelClass = directedStatisticsModelClass;
    }

    public String getDirectedStatisticsStorageClass()
    {
	return this.directedStatisticsStorageClass;
    }

    public void setDirectedStatisticsStorageClass(String directedStatisticsStorageClass)
    {
	this.directedStatisticsStorageClass = directedStatisticsStorageClass;
    }

    public String getTestStatisticsModelClass()
    {
	return this.testStatisticsModelClass;
    }

    public void setTestStatisticsModelClass(String testStatisticsModelClass)
    {
	this.testStatisticsModelClass = testStatisticsModelClass;
    }

    public String getTestStatisticsStorageClass()
    {
	return this.testStatisticsStorageClass;
    }

    public void setTestStatisticsStorageClass(String testStatisticsStorageClass)
    {
	this.testStatisticsStorageClass = testStatisticsStorageClass;
    }

    public float getFontSize()
    {
	return this.fontSize;
    }

    public void setFontSize(float size)
    {
	this.fontSize = fontSize;
    }

    public String getLoginModelClass()
    {
	return this.loginModelClass;
    }

    public void setLoginModelClass(String loginModelClass)
    {
	this.loginModelClass = loginModelClass;
    }

    public String getLoginViewClass()
    {
	return this.loginViewClass;
    }

    public void setLoginViewClass(String loginViewClass)
    {
	this.loginViewClass = loginViewClass;
    }

    public String getSpellingModelClass()
    {
	return this.spellingModelClass;
    }

    public void setSpellingModelClass(String spellingModelClass)
    {
	this.spellingModelClass = spellingModelClass;
    }

    public String getSpellingViewClass()
    {
	return this.spellingViewClass;
    }

    public void setSpellingViewClass(String spellingViewClass)
    {
	this.spellingViewClass = spellingViewClass;
    }
}
