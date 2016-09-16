/*
 * WordController.java
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

import java.util.Map;

public interface WordController
{
    public PreferenceController getPreferenceController();

    public LogView getLogView();

    public WordModel getWordModel();
    public WordStorage getWordStorage();
    public WordAudio getWordAudio();
    public WordView getWordView();

    public StudentController getStudentController();
    public void setStudentController(StudentController studentController);

    public boolean isSafeToQuit();

    public void openView();
    public void closeView();

    public void newWord();
    public void load(String id);
    public void loadDoneGood();
    public void loadDoneBad();
    public void unload();
    public void modified();
    public void save();
    public void updateModelDone();
    public void saveDone();
    public void delete();
    public void deleteDone();

    public void recordStartWord();
    public void recordStopWord();
    public void playStartWord();
    public void playStopWord();

    public void recordStartedWord();
    public void recordStoppedWord();
    public void playStartedWord();
    public void playStoppedWord();

    public void showTestingStatistics();
    public void showDirectedStatistics();
    public void displayAllWordsReport();
}
