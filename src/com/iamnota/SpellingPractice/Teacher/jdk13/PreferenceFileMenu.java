/*
 * PreferenceFileMenu.java
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

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreferenceFileMenu
  extends JMenu
  implements ActionListener
{
    private static final String LABEL = "File";

    private static final String ITEM_SAVE = "Save";
    private static final String COMMAND_SAVE = "SAVE";
    
    private static final String ITEM_CLOSE_WINDOW = "Close window";
    private static final String COMMAND_CLOSE_WINDOW = "CLOSE_WINDOW";

    private PreferenceController preferenceController;

    private JMenuItem saveItem;
    private JMenuItem closeWindowItem;

    public PreferenceFileMenu(PreferenceController preferenceController)
    {
	super(PreferenceFileMenu.LABEL);
	
	this.preferenceController = preferenceController;
	
	saveItem = new JMenuItem(PreferenceFileMenu.ITEM_SAVE);
	saveItem.setActionCommand(PreferenceFileMenu.COMMAND_SAVE);
	saveItem.addActionListener(this);
	add(saveItem);
	
	add(new JSeparator());
	
	closeWindowItem = new JMenuItem(PreferenceFileMenu.ITEM_CLOSE_WINDOW);
	closeWindowItem.setActionCommand(PreferenceFileMenu.COMMAND_CLOSE_WINDOW);
	closeWindowItem.addActionListener(this);
	add(closeWindowItem);
    }
    
    public void opened()
    {
	this.saveItem.setEnabled(false);
	this.closeWindowItem.setEnabled(true);
    }
    
    public void closed()
    {
	this.saveItem.setEnabled(false);
	this.closeWindowItem.setEnabled(true);
    }
    
    public void modified()
    {
	this.saveItem.setEnabled(true);
    }

    public void saved()
    {
	this.saveItem.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(PreferenceFileMenu.COMMAND_SAVE))
	{
	    this.preferenceController.save();
	    return;
	}
	
	if (action.equals(PreferenceFileMenu.COMMAND_CLOSE_WINDOW))
	{
	    this.preferenceController.closeView();
	    return;
	}
    }
}
