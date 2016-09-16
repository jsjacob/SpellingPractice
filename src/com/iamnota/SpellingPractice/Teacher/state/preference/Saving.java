/*
 * Saving.java
 *
 * Copyright (C) 2003-2004 John S. Jacob <jsjacob@iamnota.com>
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

package com.iamnota.SpellingPractice.Teacher.state.preference;

import com.iamnota.SpellingPractice.Teacher.PreferenceController;
import com.iamnota.SpellingPractice.Teacher.PreferenceStorage;
import com.iamnota.SpellingPractice.Teacher.PreferenceView;

import java.io.IOException;

public class Saving
    extends State
{
    private static Saving me;

    protected Saving()
    {
	;
    }

    public static synchronized Saving getInstance()
    {
	if (Saving.me == null)
	{
	    Saving.me = new Saving();
	}

	return Saving.me;
    }

    public String getName()
    {
	return "Saving";
    }

    protected class SavingThread
	extends Thread
    {
	private PreferenceController preference;

	public SavingThread(PreferenceController preference)
	{
	    this.preference = preference;
	}

	public void run()
	{
	    try
	    {
		PreferenceStorage preferenceStorage = preference.getStorage();
		preferenceStorage.save();
	    }

	    catch (IOException e)
	    {
		this.preference.getLogView().debug("preference.state.Saving.SavingThread.run(): couldn't save preferences");
		e.printStackTrace(this.preference.getLogView().getDebugStream());
		this.preference.getLogView().error("Couldn't save preferences: " + e.getMessage());
	    }

	    this.preference.saveDone();
	}
    }

    public State updateModelDone(PreferenceController preference)
    {
	new SavingThread(preference).start();
	return this;
    }

    public State saveDone(PreferenceController preference)
    {
	PreferenceView preferenceView = preference.getView();
	preferenceView.saved();

	return IdleNotModified.getInstance();
    }
}
