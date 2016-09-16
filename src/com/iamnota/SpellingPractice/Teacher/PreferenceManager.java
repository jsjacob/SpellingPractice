/*
 * PreferenceManager.java
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

import com.iamnota.SpellingPractice.common.ClassUtils;

import com.iamnota.SpellingPractice.Teacher.state.preference.State;
import com.iamnota.SpellingPractice.Teacher.state.preference.IdleNotModified;

import java.io.File;
import java.io.IOException;

public class PreferenceManager
    implements PreferenceController
{
    // Boot-strap using JDK 1.1 implementation.
    // This is the lowest-common-denominator implementation.
    private static final String PREFERENCE_MODEL = "com.iamnota.SpellingPractice.Teacher.jdk11.PreferenceModelImpl";
    private static final String PREFERENCE_STORAGE = "com.iamnota.SpellingPractice.Teacher.jdk11.PreferenceStorageImpl";

    private PreferenceModel preferenceModel;
    private PreferenceStorage preferenceStorage;
    private PreferenceView preferenceView;

    private LogView logView;

    private StudentController studentController;
    private WordController wordController;
    
    private State state;

    private PreferenceManager() {}

    public PreferenceManager(LogView logView, File preferenceFile)
    {
	this.logView = logView;

	try
	{
	    // Boot-strap preferences

	    this.preferenceModel = (PreferenceModel) ClassUtils.instantiateObject(PREFERENCE_MODEL, new Object[] {});
	    this.preferenceStorage = (PreferenceStorage) ClassUtils.instantiateObject(PREFERENCE_STORAGE, new Object[] { this, this.preferenceModel, preferenceFile });
	    
	    try
	    {
		this.preferenceStorage.load();
	    }
	    
	    catch (IOException e)
	    {
		this.logView.debug("PreferenceManager.PreferenceManager(): couldn't load preferences (bootstrap implementation)");
		this.logView.debug(e.getMessage());
		//		e.printStackTrace(this.logView.getDebugStream());
		//		this.logView.error("Couldn't load preferences: " + e.getMessage());
	    }
	}
	    
	catch (InstantiationException e)
	{
	    this.logView.debug("PreferenceManager.PreferenceManager(): " + e.getMessage());
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't load preference classes: " + e.getMessage());
	}

	this.state = IdleNotModified.getInstance();
    }

    public PreferenceManager(PreferenceModel preferenceModel, LogView logView, File preferenceFile)
    {
	this.logView = logView;

	try
	{
	    // Load preferred classes
	    
	    this.preferenceModel = (PreferenceModel) ClassUtils.instantiateObject(preferenceModel.getPreferenceModelClass(), new Object[] {});
	    this.preferenceStorage = (PreferenceStorage) ClassUtils.instantiateObject(preferenceModel.getPreferenceStorageClass(), new Object[] { this, this.preferenceModel, preferenceFile });
	    
	    try
	    {
		this.preferenceStorage.load();

		switch (this.preferenceModel.getMode())
		{
		case PreferenceModel.MODE_TEST_AND_DIRECTED:
		case PreferenceModel.MODE_TEST:
		    this.preferenceModel.setShowTestingStatistics();
		    break;

		case PreferenceModel.MODE_DIRECTED:
		    this.preferenceModel.setShowDirectedStatistics();
		    break;

		default:
		    this.preferenceModel.setShowDirectedStatistics();
		    break;
		}
	    }
	    
	    catch (IOException e)
	    {
		this.logView.debug("PreferenceManager.PreferenceManager(): couldn't load preferences (preferred implementation)");
		this.logView.debug(e.getMessage());
		//		e.printStackTrace(this.logView.getDebugStream());
		this.logView.warning("Couldn't load preferences: " + e.getMessage());
	    }
	    
	    this.preferenceView = (PreferenceView) ClassUtils.instantiateObject(this.preferenceModel.getPreferenceViewClass(), new Object[] {this, this.preferenceModel});
	}

	catch (InstantiationException e)
	{
	    this.logView.debug("PreferenceManager.PreferenceManager(): " + e.getMessage());
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't load preference classes: " + e.getMessage());
	}

	this.state = IdleNotModified.getInstance();
    }

    public PreferenceModel getModel()
    {
	return this.preferenceModel;
    }

    public PreferenceStorage getStorage()
    {
	return this.preferenceStorage;
    }

    public PreferenceView getView()
    {
	return this.preferenceView;
    }

    public LogView getLogView()
    {
	return this.logView;
    }

    public StudentController getStudentController()
    {
	return this.studentController;
    }

    public void setStudentController(StudentController studentController)
    {
	this.studentController = studentController;
    }

    public WordController getWordController()
    {
	return this.wordController;
    }

    public void setWordController(WordController wordController)
    {
	this.wordController = wordController;
    }

    public float getFontSize()
    {
	this.logView.debug("PreferenceManager.getFontSize()");
	
	return this.preferenceModel.getFontSize();
    }

    public String getLogLevel()
    {
	return this.preferenceModel.getLogLevel();
    }

    public String getLogViewClass()
    {
	return this.preferenceModel.getLogViewClass();
    }

    public String getViewClass()
    {
	this.logView.debug("PreferenceManager.getViewClass()");

	return this.preferenceModel.getViewClass();
    }

    public String getPassword()
    {
	this.logView.debug("PreferenceManager.getPassword()");

	return this.preferenceModel.getPassword();
    }

    public String getPreferenceModelClass()
    {
	this.logView.debug("PreferenceManager.getPreferenceModelClass()");

	return this.preferenceModel.getPreferenceModelClass();
    }

    public String getPreferenceStorageClass()
    {
	this.logView.debug("PreferenceManager.getPreferenceStorageClass()");

	return this.preferenceModel.getPreferenceStorageClass();
    }

    public String getPreferenceViewClass()
    {
	this.logView.debug("PreferenceManager.getPreferenceViewClass()");

	return this.preferenceModel.getPreferenceViewClass();
    }

    public String getWordViewClass()
    {
	this.logView.debug("PreferenceManager.getWordViewClass()");

	return this.preferenceModel.getWordViewClass();
    }

    public String getWordAudioClass()
    {
	this.logView.debug("PreferenceManager.getWordAudioClass()");

	return this.preferenceModel.getWordAudioClass();
    }

    public int getWordAudioMaxDurationInSeconds()
    {
	this.logView.debug("PreferenceManager.getWordaudioMaxDurationInSeconds()");
	
	return this.preferenceModel.getWordAudioMaxDurationInSeconds();
    }

    public String getWordModelClass()
    {
	this.logView.debug("PreferenceManager.getWordModelClass()");

	return this.preferenceModel.getWordModelClass();
    }

    public String getWordStorageClass()
    {
	this.logView.debug("PreferenceManager.getWordStorageClass()");

	return this.preferenceModel.getWordStorageClass();
    }

    public String getWordStorageDirectory()
    {
	this.logView.debug("PreferenceManager.getWordStorageDirectory()");

	return this.preferenceModel.getWordStorageDirectory();
    }

    public String getStudentViewClass()
    {
	this.logView.debug("PreferenceManager.getStudentViewClass()");

	return this.preferenceModel.getStudentViewClass();
    }

    public String getStudentModelClass()
    {
	this.logView.debug("PreferenceManager.getStudentModelClass()");

	return this.preferenceModel.getStudentModelClass();
    }

    public String getStudentStorageClass()
    {
	this.logView.debug("PreferenceManager.getStudentStorageClass()");

	return this.preferenceModel.getStudentStorageClass();
    }

    public String getStudentStorageDirectory()
    {
	this.logView.debug("PreferenceManager.getStudentStorageDirectory()");

	return this.preferenceModel.getStudentStorageDirectory();
    }

    public String getTestStatisticsStorageClass()
    {
	this.logView.debug("PreferenceManager.getTestStatisticsStorageClass()");

	return this.preferenceModel.getTestStatisticsStorageClass();
    }

    public String getDirectedStatisticsStorageClass()
    {
	this.logView.debug("PreferenceManager.getDirectedStatisticsStorageClass()");

	return this.preferenceModel.getDirectedStatisticsStorageClass();
    }

    public String getStudentAutoLoginId()
    {
	this.logView.debug("PreferenceManager.getStudentAutoLoginId()");

	return this.preferenceModel.getStudentAutoLoginId();
    }

    public void setStudentAutoLoginId(String studentId)
    {
	this.logView.debug("PreferenceManager.setStudentAutoLoginId()");

	this.preferenceModel.setStudentAutoLoginId(studentId);
    }

    public int getMode()
    {
	this.logView.debug("PreferenceManager.getMode()");

	return this.preferenceModel.getMode();
    }

    public void setMode(int mode)
    {
	this.logView.debug("PreferenceManager.setMode()");

	this.preferenceModel.setMode(mode);
    }

    public synchronized boolean isSafeToQuit()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".isSafeToQuit()");
	return this.state.isSafeToQuit();
    }	

    public synchronized void openView()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".openView()");
	this.state = this.state.openView(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void closeView()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".closeView()");
	this.state = this.state.closeView(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void modified()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".modified()");
	this.state = this.state.modified(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void save()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".save()");
	this.state = this.state.save(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void updateModelDone()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".updateModelDone()");
	this.state = this.state.updateModelDone(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void saveDone()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".saveDone()");
	this.state = this.state.saveDone(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void showTestingStatistics()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".showTestingStatistics()");
	this.state = this.state.showTestingStatistics(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }

    public synchronized void showDirectedStatistics()
    {
	this.logView.debug("Preference: " + this.state.getName() + ".showDirectedStatistics()");
	this.state = this.state.showDirectedStatistics(this);
	this.logView.debug("Preference: --> " + this.state.getName());
    }
}
