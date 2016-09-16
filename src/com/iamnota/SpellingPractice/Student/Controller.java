/*
 * Controller.java
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

public interface Controller
{
    public PreferenceModel getPreferenceModel();
    public PreferenceStorage getPreferenceStorage();

    public View getView();
    public LogView getLogView();

    public LoginModel getLoginModel();
    public LoginView getLoginView();

    public SpellingModel getSpellingModel();
    public SpellingView getSpellingView();

    public StudentModel getStudentModel();
    public StudentStorage getStudentStorage();

    public WordModel getWordModel();
    public WordStorage getWordStorage();
    public WordAudio getWordAudio();

    public DirectedStatisticsModel getDirectedStatisticsModel();
    public DirectedStatisticsStorage getDirectedStatisticsStorage();

    public TestStatisticsModel getTestStatisticsModel();
    public TestStatisticsStorage getTestStatisticsStorage();

    public void doAbout();
    public void aboutDone();

    public void login();
    public void logout();
    public void listen();
    public void check();
    public void timeout();

    public void loadStarted();
    public void loadStoppedSuccessfully();
    public void loadStoppedUnsuccessfully();
    public void listenStarted();
    public void listenStopped();
    public void showResultStarted();
    public void showResultStopped();
    public void showNoWordsStarted();
    public void showNoWordsStopped();

    public boolean statisticsIsShouldReload();
    public void statisticsLoad();
    public String statisticsGetNextWordId();
    public void statisticsUpdateCorrect();
    public void statisticsUpdateIncorrect();
    public void statisticsSave();

    public void quit();
}
