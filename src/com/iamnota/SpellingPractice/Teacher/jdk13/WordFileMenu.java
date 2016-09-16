/*
 * WordFileMenu.java
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

import com.iamnota.SpellingPractice.Teacher.WordController;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordFileMenu
  extends JMenu
  implements ActionListener
{
    private static final String LABEL = "File";

    private static final String ITEM_NEW = "New";
    private static final String COMMAND_NEW = "NEW";
    
    private static final String ITEM_SAVE = "Save";
    private static final String COMMAND_SAVE = "SAVE";
    
    private static final String ITEM_DELETE = "Delete...";
    private static final String COMMAND_DELETE = "DELETE";
    
    private static final String ITEM_CLOSE_WINDOW = "Close window";
    private static final String COMMAND_CLOSE_WINDOW = "CLOSE_WINDOW";

    private WordController wordController;

    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem deleteItem;
    private JMenuItem closeWindowItem;

    public WordFileMenu(WordController wordController)
    {
	super(WordFileMenu.LABEL);
	
	this.wordController = wordController;
	
	this.newItem = new JMenuItem(WordFileMenu.ITEM_NEW);
	this.newItem.setActionCommand(WordFileMenu.COMMAND_NEW);
	this.newItem.addActionListener(this);
	add(this.newItem);
	
	this.saveItem = new JMenuItem(WordFileMenu.ITEM_SAVE);
	this.saveItem.setActionCommand(WordFileMenu.COMMAND_SAVE);
	this.saveItem.addActionListener(this);
	add(this.saveItem);
	
	this.deleteItem = new JMenuItem(WordFileMenu.ITEM_DELETE);
	this.deleteItem.setActionCommand(WordFileMenu.COMMAND_DELETE);
	this.deleteItem.addActionListener(this);
	add(this.deleteItem);
	
	add(new JSeparator());
	
	this.closeWindowItem = new JMenuItem(WordFileMenu.ITEM_CLOSE_WINDOW);
	this.closeWindowItem.setActionCommand(WordFileMenu.COMMAND_CLOSE_WINDOW);
	this.closeWindowItem.addActionListener(this);
	add(this.closeWindowItem);
    }

    public void openView()
    {
    }

    public void closeView()
    {
    }

    public void opened()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(false);
	this.deleteItem.setEnabled(true);
	this.closeWindowItem.setEnabled(true);
    }

    public void closed()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(false);
	this.deleteItem.setEnabled(false);
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

    public void deleted()
    {
	this.saveItem.setEnabled(false);
    }

    public void recordStartedWord()
    {
	this.newItem.setEnabled(false);
	this.saveItem.setEnabled(false);
	this.deleteItem.setEnabled(false);
	this.closeWindowItem.setEnabled(true);
    }

    public void recordStoppedWord()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(true);
	this.deleteItem.setEnabled(true);
	this.closeWindowItem.setEnabled(true);
    }

    public void playStartedWordNotModified()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(false);
	this.deleteItem.setEnabled(false);
	this.closeWindowItem.setEnabled(true);
    }

    public void playStoppedWordNotModified()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(false);
	this.deleteItem.setEnabled(true);
	this.closeWindowItem.setEnabled(true);
    }
    
    public void playStartedWordModified()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(true);
	this.deleteItem.setEnabled(false);
	this.closeWindowItem.setEnabled(true);
    }

    public void playStoppedWordModified()
    {
	this.newItem.setEnabled(true);
	this.saveItem.setEnabled(true);
	this.deleteItem.setEnabled(true);
	this.closeWindowItem.setEnabled(true);
    }
    
    public void showTestingStatistics()
    {
    }

    public void showDirectedStatistics()
    {
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(WordFileMenu.COMMAND_NEW))
	{
	    this.wordController.newWord();
	    return;
	}
	
	if (action.equals(WordFileMenu.COMMAND_SAVE))
	{
	    this.wordController.save();
	    return;
	}
	
	if (action.equals(WordFileMenu.COMMAND_DELETE))
	{
	    new WordDeleteWindow(this.wordController);
	    return;
	}
	
	if (action.equals(WordFileMenu.COMMAND_CLOSE_WINDOW))
	{
	    this.wordController.closeView();
	    return;
	}
    }
}
