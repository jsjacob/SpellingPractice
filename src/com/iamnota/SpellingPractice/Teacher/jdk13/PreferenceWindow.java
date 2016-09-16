/*
 * PreferenceWindow.java
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

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PreferenceWindow
    extends JFrame
{
    private static final String TITLE_NOT_MODIFIED = "Preferences - Teacher - SpellingPractice";
    private static final String TITLE_MODIFIED = "Preferences * - Teacher - SpellingPractice";

    private PreferenceController preferenceController;
    private PreferenceModel preferenceModel;

    private PreferenceMenuBar preferenceMenuBar;
    private PreferencePanel preferencePanel;

    public PreferenceWindow(PreferenceController preferenceController, PreferenceModel preferenceModel)
    {
	super(TITLE_NOT_MODIFIED);
	//    frame.setResizable(false);

	this.preferenceController = preferenceController;
	this.preferenceModel = preferenceModel;

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		getPreferenceController().closeView();
	    }
	};
	addWindowListener(l);

	this.preferenceMenuBar = new PreferenceMenuBar(preferenceController);
	setJMenuBar(this.preferenceMenuBar);

	Container contentPane = getContentPane();
	this.preferencePanel = new PreferencePanel(preferenceController, preferenceModel);
	contentPane.add(this.preferencePanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/2.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	hide();
    }

    protected PreferenceController getPreferenceController()
    {
	return this.preferenceController;
    }

    public void updateModel()
    {
	this.preferencePanel.updateModel();
    }

    public void opened()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.preferenceMenuBar.opened();
	this.preferencePanel.opened();
    }

    public void closed()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.preferenceMenuBar.closed();
	this.preferencePanel.closed();
    }

    public void modified()
    {
	setTitle(TITLE_MODIFIED);
	this.preferenceMenuBar.modified();
	this.preferencePanel.modified();
    }

    public void saved()
    {
	setTitle(TITLE_NOT_MODIFIED);
	this.preferenceMenuBar.saved();
	this.preferencePanel.saved();
    }
}
