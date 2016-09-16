/*
 * WordManager.java
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

import com.iamnota.SpellingPractice.Teacher.state.word.State;
import com.iamnota.SpellingPractice.Teacher.state.word.Empty;

public class WordManager
    implements WordController
{
    private PreferenceController preference;
    private LogView logView;

    private WordModel wordModel;

    private WordView wordView;
    private WordAudio wordAudio;
    private WordStorage wordStorage;

    private StudentController studentController;

    private State state;

    public WordManager(PreferenceController preference, LogView logView)
    {
	this.preference = preference;
	this.logView = logView;

	try
	{
	    this.wordModel = (WordModel) ClassUtils.instantiateObject(this.preference.getWordModelClass(), new Object[] {});
	    
	    this.wordView = (WordView) ClassUtils.instantiateObject(this.preference.getWordViewClass(), new Object[] {this, this.wordModel});
	    this.wordView.closed();
	    
	    this.wordAudio = (WordAudio) ClassUtils.instantiateObject(this.preference.getWordAudioClass(), new Object[] {this, this.wordModel});
	    this.wordStorage = (WordStorage) ClassUtils.instantiateObject(this.preference.getWordStorageClass(), new Object[] {this, this.wordModel});
	}

	catch (InstantiationException e)
	{
	    this.logView.debug("WordManager.WordManager(): couldn't instantiate classes");
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't instantiate Word classes: " + e.getMessage());
	}
	    
	this.state = Empty.getInstance();
    }

    public PreferenceController getPreferenceController()
    {
	return this.preference;
    }

    public LogView getLogView()
    {
	return this.logView;
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

    public WordView getWordView()
    {
	return this.wordView;
    }

    public StudentController getStudentController()
    {
	return this.studentController;
    }

    public void setStudentController(StudentController studentController)
    {
	this.studentController = studentController;
    }

    public synchronized boolean isSafeToQuit()
    {
	this.logView.debug("Word: " + this.state.getName() + ".isSafeToQuit()");
	return this.state.isSafeToQuit();
    }

    public synchronized void openView()
    {
	this.logView.debug("Word: " + this.state.getName() + ".openView()");
	this.state = this.state.openView(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void closeView()
    {
	this.logView.debug("Word: " + this.state.getName() + ".closeView()");
	this.state = this.state.closeView(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void newWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".newWord()");
	this.state = this.state.newWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void load(String id)
    {
	this.logView.debug("Word: " + this.state.getName() + ".load()");
	this.state = this.state.load(this, id);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void loadDoneGood()
    {
	this.logView.debug("Word: " + this.state.getName() + ".loadDoneGood()");
	this.state = this.state.loadDoneGood(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void loadDoneBad()
    {
	this.logView.debug("Word: " + this.state.getName() + ".loadDoneBad()");
	this.state = this.state.loadDoneBad(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void unload()
    {
	this.logView.debug("Word: " + this.state.getName() + ".unload()");
	this.state = this.state.unload(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void modified()
    {
	this.logView.debug("Word: " + this.state.getName() + ".modified()");
	this.state = this.state.modified(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void save()
    {
	this.logView.debug("Word: " + this.state.getName() + ".save()");
	this.state = this.state.save(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void updateModelDone()
    {
	this.logView.debug("Word: " + this.state.getName() + ".updateModelDone()");
	this.state = this.state.updateModelDone(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void saveDone()
    {
	this.logView.debug("Word: " + this.state.getName() + ".saveDone()");
	this.state = this.state.saveDone(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void delete()
    {
	this.logView.debug("Word: " + this.state.getName() + ".delete()");
	this.state = this.state.delete(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void deleteDone()
    {
	this.logView.debug("Word: " + this.state.getName() + ".deleteDone()");
	this.state = this.state.deleteDone(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void recordStartWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".recordStartWord()");
	this.state = this.state.recordStartWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void recordStopWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".recordStopWord()");
	this.state = this.state.recordStopWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void playStartWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".playStartWord()");
	this.state = this.state.playStartWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void playStopWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".playStopWord()");
	this.state = this.state.playStopWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void recordStartedWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".recordStartedWord()");
	this.state = this.state.recordStartedWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void recordStoppedWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".recordStoppedWord()");
	this.state = this.state.recordStoppedWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void playStartedWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".playStartedWord()");
	this.state = this.state.playStartedWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void playStoppedWord()
    {
	this.logView.debug("Word: " + this.state.getName() + ".playStoppedWord()");
	this.state = this.state.playStoppedWord(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void showTestingStatistics()
    {
	this.logView.debug("Word: " + this.state.getName() + ".showTestingStatistics()");
	this.state = this.state.showTestingStatistics(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void showDirectedStatistics()
    {
	this.logView.debug("Word: " + this.state.getName() + ".showDirectedStatistics()");
	this.state = this.state.showDirectedStatistics(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }

    public synchronized void displayAllWordsReport()
    {
	this.logView.debug("Word: " + this.state.getName() + ".displayAllWordsReport()");
	this.state = this.state.displayAllWordsReport(this);
	this.logView.debug("Word: --> " + this.state.getName());
    }
}
