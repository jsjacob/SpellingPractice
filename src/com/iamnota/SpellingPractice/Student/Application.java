/*
 * Student.java
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

import com.iamnota.SpellingPractice.Student.state.Starting;

import com.iamnota.SpellingPractice.Student.state.statistics.Off;
import com.iamnota.SpellingPractice.Student.state.statistics.Directed;
import com.iamnota.SpellingPractice.Student.state.statistics.Test;
import com.iamnota.SpellingPractice.Student.state.statistics.TestAndDirected;

import com.iamnota.SpellingPractice.common.ClassUtils;

import java.io.File;
import java.io.PrintStream;
import java.io.IOException;


/**
 *  Student interface to SpellingPractice.
 *  Play spelling words and usage in sentences.
 *  Track correctly and incorrectly spelled words.
 *
 * @version @(#)com.iamnota.SpellingPractice.Student.Application.java	0.01	2002-09-18
 * @author "John S. Jacob" <jsjacob@iamnota.com>
 */
public class Application
    implements Controller
{
    // Default preference file
    private static final String PREFERENCE_FILE_NAME = "spstudent.rc";

    // Boot-strap using JDK 1.1 implementation.
    // This is the lowest-common-denominator implementation.
    private static final String PREFERENCE_MODEL = "com.iamnota.SpellingPractice.Student.jdk11.PreferenceModelImpl";
    private static final String PREFERENCE_STORAGE = "com.iamnota.SpellingPractice.Student.jdk11.PreferenceStorageImpl";

    private PreferenceModel preferenceModel;
    private PreferenceStorage preferenceStorage;

    private View view;
    private LogView logView;

    private LoginModel loginModel;
    private LoginView loginView;

    private SpellingModel spellingModel;
    private SpellingView spellingView;

    private StudentModel studentModel;
    private StudentStorage studentStorage;

    private WordModel wordModel;
    private WordStorage wordStorage;
    private WordAudio wordAudio;

    private DirectedStatisticsModel directedStatisticsModel;
    private DirectedStatisticsStorage directedStatisticsStorage;

    private TestStatisticsModel testStatisticsModel;
    private TestStatisticsStorage testStatisticsStorage;

    private com.iamnota.SpellingPractice.Student.state.State state;
    private com.iamnota.SpellingPractice.Student.state.statistics.State statisticsState;

    public Application(File preferenceFile)
    {
	this.logView = new SimpleLogView();

	try
	{
	    loadPreferenceBootstrap(preferenceFile);
	    
	    loadView();
	    loadLogView();
	    loadPreferencePreferred(preferenceFile);
	    loadWord();
	    loadStudent();
	    loadLogin();
	    loadSpelling();

	    switch (this.preferenceModel.getMode())
	    {
	    case PreferenceModel.MODE_RANDOM:
		this.statisticsState = Off.getInstance();
		break;

	    case PreferenceModel.MODE_DIRECTED:
		loadDirectedStatistics();
		this.statisticsState = Directed.getInstance();
		break;

	    case PreferenceModel.MODE_TEST:
		loadTestStatistics();
		this.statisticsState = Test.getInstance();
		break;

	    case PreferenceModel.MODE_TEST_AND_DIRECTED:
		loadDirectedStatistics();
		loadTestStatistics();
		this.statisticsState = TestAndDirected.getInstance();
		break;
	    }
	}

	catch (InstantiationException e)
	{
	    e.printStackTrace(System.out);
	    this.logView.debug("Application.Application(): couldn't instantiate classes");
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't instantiate classes: " + e.getMessage());
	}

	this.state = Starting.getInstance();

	doAbout();
    }

    private void loadPreferenceBootstrap(File preferenceFile)
	throws InstantiationException
    {
	// Boot-strap preferences

	this.preferenceModel = (PreferenceModel) ClassUtils.instantiateObject(PREFERENCE_MODEL, new Object[] {});
	this.preferenceStorage = (PreferenceStorage) ClassUtils.instantiateObject(PREFERENCE_STORAGE, new Object[] { this.logView, this.preferenceModel, preferenceFile });

	try
	{
	    this.preferenceStorage.load();
	}

	catch (IOException e)
	{
	    this.logView.debug("Student.Application.loadPreference(): couldn't load preferences (bootstrap implementation)");
	    this.logView.debug(e.getMessage());
	    //	    e.printStackTrace(this.logView.getDebugStream());
	    //	    this.logView.warning("Couldn't load preferences: " + e.getMessage());
	}
    }

    private void loadPreferencePreferred(File preferenceFile)
	throws InstantiationException
    {
	// Load preferred classes

	this.preferenceModel = (PreferenceModel) ClassUtils.instantiateObject(this.preferenceModel.getPreferenceModelClass(), new Object[] {});
	this.preferenceStorage = (PreferenceStorage) ClassUtils.instantiateObject(this.preferenceModel.getPreferenceStorageClass(), new Object[] { this.logView, this.preferenceModel, preferenceFile });

	try
	{
	    this.preferenceStorage.load();
	}

	catch (IOException e)
	{
	    this.logView.debug("Student.Application.loadPreference(): couldn't load preferences (preferred implementation)");
	    this.logView.debug(e.getMessage());
	    //	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.warning("Couldn't load preferences: " + e.getMessage());
	}
    }

    private void loadView()
	throws InstantiationException
    {
	this.view = (View) ClassUtils.instantiateObject(this.preferenceModel.getViewClass(), new Object[] { this });
    }

    private void loadLogView()
	throws InstantiationException
    {
	this.logView = (LogView) ClassUtils.instantiateObject(this.preferenceModel.getLogViewClass(), new Object[] { this });
    }

    private void loadLogin()
	throws InstantiationException
    {
	this.loginModel = (LoginModel) ClassUtils.instantiateObject(this.preferenceModel.getLoginModelClass(), new Object[] {});
	this.loginView = (LoginView) ClassUtils.instantiateObject(this.preferenceModel.getLoginViewClass(), new Object[] { this, this.loginModel });
    }

    private void loadSpelling()
	throws InstantiationException
    {
	this.spellingModel = (SpellingModel) ClassUtils.instantiateObject(this.preferenceModel.getSpellingModelClass(), new Object[] {});
	this.spellingView = (SpellingView) ClassUtils.instantiateObject(this.preferenceModel.getSpellingViewClass(), new Object[] { this, this.spellingModel });
    }

    private void loadStudent()
	throws InstantiationException
    {
	this.studentModel = (StudentModel) ClassUtils.instantiateObject(this.preferenceModel.getStudentModelClass(), new Object[] {});
	this.studentStorage = (StudentStorage) ClassUtils.instantiateObject(this.preferenceModel.getStudentStorageClass(), new Object[] { this, this.studentModel });
    }

    private void loadWord()
	throws InstantiationException
    {
	this.wordModel = (WordModel) ClassUtils.instantiateObject(this.preferenceModel.getWordModelClass(), new Object[] {});
	this.wordStorage = (WordStorage) ClassUtils.instantiateObject(this.preferenceModel.getWordStorageClass(), new Object[] { this, this.wordModel });
	this.wordAudio = (WordAudio) ClassUtils.instantiateObject(this.preferenceModel.getWordAudioClass(), new Object[] { this, this.wordModel });
    }


    private void loadDirectedStatistics()
	throws InstantiationException
    {
	this.directedStatisticsModel = (DirectedStatisticsModel) ClassUtils.instantiateObject(this.preferenceModel.getDirectedStatisticsModelClass(), new Object[] {});
	this.directedStatisticsStorage = (DirectedStatisticsStorage) ClassUtils.instantiateObject(this.preferenceModel.getDirectedStatisticsStorageClass(), new Object[] { this, this.directedStatisticsModel });
    }

    private void loadTestStatistics()
	throws InstantiationException
    {
	this.testStatisticsModel = (TestStatisticsModel) ClassUtils.instantiateObject(this.preferenceModel.getTestStatisticsModelClass(), new Object[] {});
	this.testStatisticsStorage = (TestStatisticsStorage) ClassUtils.instantiateObject(this.preferenceModel.getTestStatisticsStorageClass(), new Object[] { this, this.testStatisticsModel });
    }

    public PreferenceModel getPreferenceModel()
    {
	return this.preferenceModel;
    }

    public PreferenceStorage getPreferenceStorage()
    {
	return this.preferenceStorage;
    }

    public View getView()
    {
	return this.view;
    }

    public LogView getLogView()
    {
	return this.logView;
    }

    public LoginModel getLoginModel()
    {
	return this.loginModel;
    }

    public LoginView getLoginView()
    {
	return this.loginView;
    }

    public SpellingModel getSpellingModel()
    {
	return this.spellingModel;
    }

    public SpellingView getSpellingView()
    {
	return this.spellingView;
    }

    public StudentModel getStudentModel()
    {
	return this.studentModel;
    }

    public StudentStorage getStudentStorage()
    {
	return this.studentStorage;
    }

    public WordModel getWordModel()
    {
	return this.wordModel;
    }

    public WordStorage getWordStorage()
    {
	return this.wordStorage;
    }

    public WordAudio getWordAudio()
    {
	return this.wordAudio;
    }

    public DirectedStatisticsModel getDirectedStatisticsModel()
    {
	return this.directedStatisticsModel;
    }

    public DirectedStatisticsStorage getDirectedStatisticsStorage()
    {
	return this.directedStatisticsStorage;
    }

    public TestStatisticsModel getTestStatisticsModel()
    {
	return this.testStatisticsModel;
    }

    public TestStatisticsStorage getTestStatisticsStorage()
    {
	return this.testStatisticsStorage;
    }

    public synchronized void doAbout()
    {
	this.logView.debug("Application: " + this.state.getName() + ".doAbout()");
	this.state = this.state.doAbout(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void aboutDone()
    {
	this.logView.debug("Application: " + this.state.getName() + ".aboutDone()");
	this.state = this.state.aboutDone(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void login()
    {
	this.logView.debug("Application: " + this.state.getName() + ".login()");
	this.state = this.state.login(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void logout()
    {
	this.logView.debug("Application: " + this.state.getName() + ".logout()");
	this.state = this.state.logout(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void listen()
    {
	this.logView.debug("Application: " + this.state.getName() + ".listen()");
	this.state = this.state.listen(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void check()
    {
	this.logView.debug("Application: " + this.state.getName() + ".check()");
	this.state = this.state.check(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void timeout()
    {
	this.logView.debug("Application: " + this.state.getName() + ".timeout()");
	this.state = this.state.timeout(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void loadStarted()
    {
	this.logView.debug("Application: " + this.state.getName() + ".loadStarted()");
	this.state = this.state.loadStarted(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void loadStoppedSuccessfully()
    {
	this.logView.debug("Application: " + this.state.getName() + ".loadStoppedSuccessfully()");
	this.state = this.state.loadStoppedSuccessfully(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void loadStoppedUnsuccessfully()
    {
	this.logView.debug("Application: " + this.state.getName() + ".loadStoppedUnsuccessfully()");
	this.state = this.state.loadStoppedUnsuccessfully(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void listenStarted()
    {
	this.logView.debug("Application: " + this.state.getName() + ".listenStarted()");
	this.state = this.state.listenStarted(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void listenStopped()
    {
	this.logView.debug("Application: " + this.state.getName() + ".listenStopped()");
	this.state = this.state.listenStopped(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void showResultStarted()
    {
	this.logView.debug("Application: " + this.state.getName() + ".showResultStarted()");
	this.state = this.state.showResultStarted(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void showResultStopped()
    {
	this.logView.debug("Application: " + this.state.getName() + ".showResultStopped()");
	this.state = this.state.showResultStopped(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void showNoWordsStarted()
    {
	this.logView.debug("Application: " + this.state.getName() + ".showNoWordsStarted()");
	this.state = this.state.showNoWordsStarted(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized void showNoWordsStopped()
    {
	this.logView.debug("Application: " + this.state.getName() + ".showNoWordsStopped()");
	this.state = this.state.showNoWordsStopped(this);
	this.logView.debug("Application: --> " + this.state.getName());
    }

    public synchronized boolean statisticsIsShouldReload()
    {
	this.logView.debug("Statistics: " + this.statisticsState.getName() + ".isShouldReload()");
	return this.statisticsState.isShouldReload();
    }

    public synchronized void statisticsLoad()
    {
	this.logView.debug("Statistics: " + this.statisticsState.getName() + ".load()");
	this.statisticsState = this.statisticsState.load(this);
	this.logView.debug("Statistics: --> " + this.statisticsState.getName());
    }

    public synchronized String statisticsGetNextWordId()
    {
	this.logView.debug("Statistics: " + this.statisticsState.getName() + ".getNextWordId()");
	return this.statisticsState.getNextWordId(this);
    }

    public synchronized void statisticsUpdateCorrect()
    {
	this.logView.debug("Statistics: " + this.statisticsState.getName() + ".updateCorrect()");
	this.statisticsState = this.statisticsState.updateCorrect(this);
	this.logView.debug("Statistics: --> " + this.statisticsState.getName());
    }

    public synchronized void statisticsUpdateIncorrect()
    {
	this.logView.debug("Statistics: " + this.statisticsState.getName() + ".updateIncorrect()");
	this.statisticsState = this.statisticsState.updateIncorrect(this);
	this.logView.debug("Statistics: --> " + this.statisticsState.getName());
    }

    public synchronized void statisticsSave()
    {
	this.logView.debug("Statistics: " + this.statisticsState.getName() + ".save()");
	this.statisticsState = this.statisticsState.save(this);
	this.logView.debug("Statistics: --> " + this.statisticsState.getName());
    }

    public void quit()
    {
	this.logView.debug("Application: quit()");
	System.exit(0);
    }


    public static void main(String args[])
    {
	if (args.length > 1)
	{
	    usage();
	    System.exit(1);
	}

	String preferenceFileName = PREFERENCE_FILE_NAME;

	if (args.length == 1)
	{
	    preferenceFileName = args[0];
	}

	File preferenceFile = new File(preferenceFileName);
	Application student = new Application(preferenceFile);
    }

    protected static void usage()
    {
	System.out.println("Usage: Student.Application [ _preferenceFile_ ]");
    }

    protected class SimpleLogView
	implements LogView
    {
	public void debug(String message)
	{
	    ;
	}

	public void warning(String message)
	{
	    System.out.println(message);
	}

	public void error(String message)
	{
	    System.out.println(message);
	}

	public PrintStream getDebugStream()
	{
	    return System.out;
	}
    }
} 
