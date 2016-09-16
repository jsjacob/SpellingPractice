/*
 * ViewImpl.java
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

import com.iamnota.SpellingPractice.Teacher.Controller;
import com.iamnota.SpellingPractice.Teacher.View;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import com.iamnota.SpellingPractice.Teacher.version;

import com.iamnota.SpellingPractice.common.TextUtils;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIDefaults;
import java.awt.Font;

import java.util.Enumeration;


public class ViewImpl
    implements View
{
    private static final int TEXT_COLUMNS = 80;

    private Controller controller;

    private Window window;

    public ViewImpl(Controller controller)
    {
	this.controller = controller;

	PreferenceController preference = this.controller.getPreferenceController();

        UIDefaults uiDefaults = UIManager.getDefaults();
        Enumeration keys = uiDefaults.keys();
      
	while (keys.hasMoreElements())
        {
	    Object key = keys.nextElement();
	    Font oldFont = uiDefaults.getFont(key);

	    if (oldFont != null)
	    {
		Font newFont = oldFont.deriveFont(preference.getFontSize());
		uiDefaults.put(key, newFont);
	    }
	}

	TextUtils.printLines(System.out, TEXT_COLUMNS, version.getInfoText());
	System.out.println();
	System.out.println();
	TextUtils.printLines(System.out, TEXT_COLUMNS, version.getLicenseText());
	System.out.println();
	System.out.println();

	this.window = new Window(controller);
    }

    protected void _displayAbout()
    {
	AboutWindow aboutWindow = new AboutWindow(null);
	this.controller.aboutDone();
    }

    protected void _show()
    {
	this.window.show();
    }

    protected void _hide()
    {
	this.window.hide();
    }

    public void displayAbout()
    {
	Runnable doAbout = new Runnable()
	    {
		public void run()
		{
		    _displayAbout();
		}
	    };

	SwingUtilities.invokeLater(doAbout);
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

    public String askPassword()
    {
	return AskPasswordWindow.askPassword(this.controller);
    }

    public void showBadPassword()
    {
	BadPasswordWindow.showBadPassword(this.controller);
    }
}
