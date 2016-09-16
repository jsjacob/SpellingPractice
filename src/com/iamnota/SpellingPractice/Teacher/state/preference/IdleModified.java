/*
 * IdleModified.java
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
import com.iamnota.SpellingPractice.Teacher.PreferenceView;

public class IdleModified
    extends State
{
    private static IdleModified me;

    protected IdleModified()
    {
	;
    }

    public static synchronized IdleModified getInstance()
    {
	if (IdleModified.me == null)
	{
	    IdleModified.me = new IdleModified();
	}

	return IdleModified.me;
    }

    public String getName()
    {
	return "IdleModified";
    }

    public State closeView(PreferenceController preference)
    {
	// ask to save changes
	PreferenceView preferenceView = preference.getView();

	if (preferenceView.isAskSaveChanges())
	{
	    preferenceView.updateModel();

	    return SavingCloseView.getInstance();
	}
	else
	{
	    return IdleNotModified.getInstance().closeView(preference);
	}
    }

    public State save(PreferenceController preference)
    {
	PreferenceView preferenceView = preference.getView();
	preferenceView.updateModel();
	return Saving.getInstance();
    }
}
