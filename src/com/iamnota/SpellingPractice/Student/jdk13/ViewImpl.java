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

package com.iamnota.SpellingPractice.Student.jdk13;

import com.iamnota.SpellingPractice.Student.View;
import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.PreferenceModel;

import com.iamnota.SpellingPractice.Student.version;

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

    public ViewImpl(Controller controller)
    {
	this.controller = controller;

	PreferenceModel preferenceModel = this.controller.getPreferenceModel();

        UIDefaults uiDefaults = UIManager.getDefaults();
        Enumeration keys = uiDefaults.keys();
      
	while (keys.hasMoreElements())
        {
	    Object key = keys.nextElement();
	    Font oldFont = uiDefaults.getFont(key);

	    if (oldFont != null)
	    {
		Font newFont = oldFont.deriveFont(preferenceModel.getFontSize());
		uiDefaults.put(key, newFont);
	    }
	}

	TextUtils.printLines(System.out, TEXT_COLUMNS, version.getInfoText());
	System.out.println();
	System.out.println();
	TextUtils.printLines(System.out, TEXT_COLUMNS, version.getLicenseText());
	System.out.println();
	System.out.println();
    }

    protected void _displayAbout()
    {
	AboutWindow aboutWindow = new AboutWindow(null);
	this.controller.aboutDone();
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
}
