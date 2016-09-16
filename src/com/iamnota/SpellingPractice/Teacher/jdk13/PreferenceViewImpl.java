/*
 * PreferenceViewImpl.java
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

package com.iamnota.SpellingPractice.Teacher.jdk13;

import com.iamnota.SpellingPractice.Teacher.PreferenceController;
import com.iamnota.SpellingPractice.Teacher.PreferenceModel;
import com.iamnota.SpellingPractice.Teacher.PreferenceView;

import javax.swing.SwingUtilities;


public class PreferenceViewImpl
    implements PreferenceView
{
    private PreferenceController preferenceController;
    private PreferenceModel preferenceModel;

    private PreferenceWindow preferenceWindow;

    public PreferenceViewImpl(PreferenceController preferenceController, PreferenceModel preferenceModel)
    {
	this.preferenceController = preferenceController;
	this.preferenceModel = preferenceModel;
	this.preferenceWindow = new PreferenceWindow(preferenceController, preferenceModel);
    }

    protected void _show()
    {
	this.preferenceWindow.show();
    }

    protected void _hide()
    {
	this.preferenceWindow.hide();
    }

    protected void _updateModel()
    {
	this.preferenceWindow.updateModel();
	this.preferenceController.updateModelDone();
    }

    protected void _opened()
    {
	this.preferenceWindow.opened();
    }

    protected void _closed()
    {
	this.preferenceWindow.closed();
    }

    protected void _modified()
    {
	this.preferenceWindow.modified();
    }

    protected void _saved()
    {
	this.preferenceWindow.saved();
    }

    protected void _dispose()
    {
	this.preferenceWindow.dispose();
    }

    public void show()
    {
	Runnable doShow = new Runnable()
	    {
		public void run()
		{
		    _show();
		}
	    };

	SwingUtilities.invokeLater(doShow);
    }

    public void hide()
    {
	Runnable doHide = new Runnable()
	    {
		public void run()
		{
		    _hide();
		}
	    };

	SwingUtilities.invokeLater(doHide);
    }

    public void updateModel()
    {
	Runnable doUpdateModel = new Runnable()
	    {
		public void run()
		{
		    _updateModel();
		}
	    };

	SwingUtilities.invokeLater(doUpdateModel);
    }

    public void opened()
    {
	Runnable doOpened = new Runnable()
	    {
		public void run()
		{
		    _opened();
		}
	    };

	SwingUtilities.invokeLater(doOpened);
    }

    public void closed()
    {
	Runnable doClosed = new Runnable()
	    {
		public void run()
		{
		    _closed();
		}
	    };

	SwingUtilities.invokeLater(doClosed);
    }

    public boolean isAskSaveChanges()
    {
	return PreferenceSaveChangesWindow.askSaveChanges(this.preferenceController);
    }

    public void modified()
    {
	Runnable doModified = new Runnable()
	    {
		public void run()
		{
		    _modified();
		}
	    };

	SwingUtilities.invokeLater(doModified);
    }

    public void saved()
    {
	Runnable doSaved = new Runnable()
	    {
		public void run()
		{
		    _saved();
		}
	    };

	SwingUtilities.invokeLater(doSaved);
    }

    public void dispose()
    {
	Runnable doDispose = new Runnable()
	    {
		public void run()
		{
		    _dispose();
		}
	    };

	SwingUtilities.invokeLater(doDispose);
    }
}
