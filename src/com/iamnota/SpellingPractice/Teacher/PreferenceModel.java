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

package com.iamnota.SpellingPractice.Teacher;

public interface PreferenceModel
{
    public static final int MODE_RANDOM = 0;
    public static final int MODE_DIRECTED = 1;
    public static final int MODE_TEST = 2;
    public static final int MODE_TEST_AND_DIRECTED = 3;

    public float getFontSize();
    public void setFontSize(float size);

    public String getViewClass();
    public void setViewClass(String viewClass);

    public String getLogLevel();
    public void setLogLevel(String logLevel);

    public String getLogViewClass();
    public void setLogViewClass(String viewClass);

    public String getPassword();
    public void setPassword(String password);

    public String getPreferenceModelClass();
    public void setPreferenceModelClass(String preferenceModelClass);

    public String getPreferenceStorageClass();
    public void setPreferenceStorageClass(String preferenceStorageClass);

    public String getPreferenceViewClass();
    public void setPreferenceViewClass(String preferenceViewClass);

    public String getWordViewClass();
    public void setWordViewClass(String wordViewClass);

    public String getWordAudioClass();
    public void setWordAudioClass(String wordAudioClass);

    public String getWordModelClass();
    public void setWordModelClass(String wordModelClass);

    public int getWordAudioMaxDurationInSeconds();
    public void setWordAudioMaxDurationInSeconds(int maxDurationInSeconds);

    public String getWordStorageClass();
    public void setWordStorageClass(String wordStorageClass);

    public String getWordStorageDirectory();
    public void setWordStorageDirectory(String wordStorageDirectory);

    public String getStudentViewClass();
    public void setStudentViewClass(String studentViewClass);

    public String getStudentModelClass();
    public void setStudentModelClass(String studentModelClass);

    public String getStudentStorageClass();
    public void setStudentStorageClass(String studentStorageClass);

    public String getStudentStorageDirectory();
    public void setStudentStorageDirectory(String studentStorageDirectory);

    public String getTestStatisticsStorageClass();
    public void setTestStatisticsStorageClass(String testStatisticsStorageClass);

    public String getDirectedStatisticsStorageClass();
    public void setDirectedStatisticsStorageClass(String testStatisticsStorageClass);

    public String getStudentAutoLoginId();
    public void setStudentAutoLoginId(String studentId);

    public int getMode();
    public void setMode(int mode);

    public void setShowTestingStatistics();
    public boolean getIsShowTestingStatistics();

    public void setShowDirectedStatistics();
    public boolean getIsShowDirectedStatistics();
}
