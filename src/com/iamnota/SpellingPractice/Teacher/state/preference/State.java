/*
 * State.java
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
import com.iamnota.SpellingPractice.Teacher.PreferenceModel;
import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.WordController;

public class State
{
    protected State()
    {
	;
    }

    public String getName()
    {
	return "State";
    }

    public boolean isSafeToQuit()
    {
	return false;
    }

    public State openView(PreferenceController preference)
    {
	PreferenceView preferenceView = preference.getView();
	preferenceView.opened();
	preferenceView.show();
	return this;
    }

    public State closeView(PreferenceController preference)
    {
	PreferenceView preferenceView = preference.getView();
	preferenceView.hide();
	preferenceView.closed();
	return this;
    }

    public State modified(PreferenceController preference)
    {
	return this;
    }

    public State save(PreferenceController preference)
    {
	return this;
    }

    public State updateModelDone(PreferenceController preference)
    {
	return this;
    }

    public State saveDone(PreferenceController preference)
    {
	return this;
    }

    public State showTestingStatistics(PreferenceController preference)
    {
	PreferenceModel preferenceModel = preference.getModel();
	preferenceModel.setShowTestingStatistics();

	preference.getStudentController().showTestingStatistics();
	preference.getWordController().showTestingStatistics();
	return this;
    }

    public State showDirectedStatistics(PreferenceController preference)
    {
	PreferenceModel preferenceModel = preference.getModel();
	preferenceModel.setShowDirectedStatistics();

	preference.getStudentController().showDirectedStatistics();
	preference.getWordController().showDirectedStatistics();
	return this;
    }
}
