/*
 * Application.java
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

import com.iamnota.SpellingPractice.Teacher.state.State;
import com.iamnota.SpellingPractice.Teacher.state.Starting;

import com.iamnota.SpellingPractice.common.ClassUtils;

import java.io.File;
import java.io.PrintStream;


/**
 *  Teacher interface to SpellingPractice.
 *
 * @version @(#)com.iamnota.SpellingPractice.Teacher.Application.java	0.01	2002-09-18
 * @author "John S. Jacob" <jsjacob@iamnota.com>
 */
public class Application
    implements Controller
{
    // Default preference file
    private static final String PREFERENCE_FILE_NAME = "spteacher.rc";

    private PreferenceController preference;

    private WordController word;
    private StudentController student;

    private View view;
    private LogView logView;

    private State state;

    public Application(File preferenceFile)
    {
	// Boot-strapping log view
	this.logView = new SimpleLogView();

	// Boot-strapping preference load
	this.preference = new PreferenceManager(this.logView, preferenceFile);
	    
	try
	{
	    this.view = (View) ClassUtils.instantiateObject(this.preference.getViewClass(), new Object[] { this });
	    this.logView = (LogView) ClassUtils.instantiateObject(this.preference.getLogViewClass(), new Object[] { this });
	}

	catch (InstantiationException e)
	{
	    this.logView.debug("Application.Application(): couldn't instantiate classes");
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't instantiate classes: " + e.getMessage());
	}

	this.state = Starting.getInstance();

	doAbout();
	
	// Preference load with View and LogView initialized
	this.preference = new PreferenceManager(this.preference.getModel(), this.logView, preferenceFile);

	try
	{
	    this.logView = (LogView) ClassUtils.instantiateObject(this.preference.getLogViewClass(), new Object[] { this });
	}
	
	catch (InstantiationException e)
	{
	    this.logView.debug("Application.Application(): couldn't instantiate classes");
	    e.printStackTrace(this.logView.getDebugStream());
	    this.logView.error("Couldn't instantiate classes: " + e.getMessage());
	}

	this.word = new WordManager(this.preference, this.logView);
	this.student = new StudentManager(this.preference, this.logView);

	this.student.setWordController(this.word);
	this.word.setStudentController(this.student);

	this.preference.setStudentController(this.student);
	this.preference.setWordController(this.word);
    }

    public PreferenceController getPreferenceController()
    {
	return this.preference;
    }

    public WordController getWordController()
    {
	return this.word;
    }

    public StudentController getStudentController()
    {
	return this.student;
    }

    public View getView()
    {
	return this.view;
    }

    public LogView getLogView()
    {
	return this.logView;
    }

    public synchronized void doPreferences()
    {
	this.logView.debug("Teacher.Application: " + this.state.getName() + ".doPreferences()");
	this.state = this.state.doPreferences(this);
	this.logView.debug("Teacher.Application: --> " + this.state.getName());
    }

    public synchronized void doWords()
    {
	this.logView.debug("Teacher.Application: " + this.state.getName() + ".doWords()");
	this.state = this.state.doWords(this);
	this.logView.debug("Teacher.Application: --> " + this.state.getName());
    }

    public synchronized void doStudents()
    {
	this.logView.debug("Teacher.Application: " + this.state.getName() + ".doStudents()");
	this.state = this.state.doStudents(this);
	this.logView.debug("Teacher.Application: --> " + this.state.getName());
    }

    public synchronized void doAbout()
    {
	this.logView.debug("Teacher.Application: " + this.state.getName() + ".doAbout()");
	this.state = this.state.doAbout(this);
	this.logView.debug("Teacher.Application: --> " + this.state.getName());
    }

    public synchronized void aboutDone()
    {
	this.logView.debug("Teacher.Application: " + this.state.getName() + ".aboutDone()");
	this.state = this.state.aboutDone(this);
	this.logView.debug("Teacher.Application: --> " + this.state.getName());
    }

    public void quit()
    {
	/* TODO: check if safe to quit
	 *
	if (!this.word.isSafeToQuit())
	{
	    this.word.closeView();
	    return;
	}

	if (!this.student.isSafeToQuit())
	{
	    this.student.closeView();
	    return;
	}

	if (!this.preference.isSafeToQuit())
	{
	    this.preference.closeView();
	    return;
	}
	*/

	this.logView.debug("Teacher.Application.quit()");
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
	Application teacher = new Application(preferenceFile);
    }

    protected static void usage()
    {
	System.out.println("Usage: Teacher.Application [ _preferenceFile_ ]");
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
