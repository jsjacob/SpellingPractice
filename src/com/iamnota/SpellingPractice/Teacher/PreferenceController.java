/*
 * PreferenceController.java
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

public interface PreferenceController
{
    public PreferenceModel getModel();
    public PreferenceStorage getStorage();
    public PreferenceView getView();

    public LogView getLogView();

    public StudentController getStudentController();
    public void setStudentController(StudentController studentController);

    public WordController getWordController();
    public void setWordController(WordController wordController);

    public float getFontSize();

    public String getViewClass();

    public String getLogLevel();
    public String getLogViewClass();

    public String getPassword();

    public String getPreferenceViewClass();

    public String getWordViewClass();
    public String getWordAudioClass();
    public int getWordAudioMaxDurationInSeconds();
    public String getWordModelClass();
    public String getWordStorageClass();
    public String getWordStorageDirectory();

    public String getStudentViewClass();
    public String getStudentModelClass();
    public String getStudentStorageClass();
    public String getStudentStorageDirectory();

    public String getTestStatisticsStorageClass();
    public String getDirectedStatisticsStorageClass();

    public String getStudentAutoLoginId();
    public void setStudentAutoLoginId(String studentId);

    public int getMode();
    public void setMode(int mode);

    public boolean isSafeToQuit();

    public void openView();
    public void closeView();

    public void modified();
    public void save();
    public void updateModelDone();
    public void saveDone();

    public void showTestingStatistics();
    public void showDirectedStatistics();
}
