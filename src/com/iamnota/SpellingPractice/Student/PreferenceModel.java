/*
 * PreferenceModel.java
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

package com.iamnota.SpellingPractice.Student;

public interface PreferenceModel
{
    public String getPreferenceModelClass();
    public void setPreferenceModelClass(String preferenceModelClass);

    public String getPreferenceStorageClass();
    public void setPreferenceStorageClass(String preferenceStorageClass);

    public String getViewClass();
    public void setViewClass(String viewClass);

    public String getLogLevel();
    public void setLogLevel(String logLevel);

    public String getLogViewClass();
    public void setLogViewClass(String viewClass);

    public String getWordAudioClass();
    public void setWordAudioClass(String wordAudioClass);

    public String getWordModelClass();
    public void setWordModelClass(String wordModelClass);

    public String getWordStorageClass();
    public void setWordStorageClass(String wordStorageClass);

    public String getWordStorageDirectory();
    public void setWordStorageDirectory(String wordStorageDirectory);

    public String getStudentModelClass();
    public void setStudentModelClass(String studentModelClass);

    public String getStudentStorageClass();
    public void setStudentStorageClass(String studentStorageClass);

    public String getStudentStorageDirectory();
    public void setStudentStorageDirectory(String studentStorageDirectory);

    public String getStudentAutoLoginId();
    public void setStudentAutoLoginId(String studentId);

    public static final int MODE_RANDOM = 0;
    public static final int MODE_DIRECTED = 1;
    public static final int MODE_TEST = 2;
    public static final int MODE_TEST_AND_DIRECTED = 3;

    public int getMode();
    public void setMode(int mode);

    public String getDirectedStatisticsModelClass();
    public void setDirectedStatisticsModelClass(String directedStatisticsModelClass);

    public String getDirectedStatisticsStorageClass();
    public void setDirectedStatisticsStorageClass(String directedStatisticsStorageClass);

    public String getTestStatisticsModelClass();
    public void setTestStatisticsModelClass(String testStatisticsModelClass);

    public String getTestStatisticsStorageClass();
    public void setTestStatisticsStorageClass(String testStatisticsStorageClass);

    public float getFontSize();
    public void setFontSize(float size);

    public String getLoginModelClass();
    public void setLoginModelClass(String loginModelClass);

    public String getLoginViewClass();
    public void setLoginViewClass(String loginViewClass);

    public String getSpellingModelClass();
    public void setSpellingModelClass(String spellingModelClass);

    public String getSpellingViewClass();
    public void setSpellingViewClass(String spellingViewClass);

}
