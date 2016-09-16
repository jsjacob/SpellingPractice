/*
 * StudentFileMenu.java
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

import com.iamnota.SpellingPractice.Teacher.StudentController;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentFileMenu
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

    private StudentController studentController;

    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem deleteItem;
    private JMenuItem closeWindowItem;

    public StudentFileMenu(StudentController studentController)
    {
	super(StudentFileMenu.LABEL);
	
	this.studentController = studentController;
	
	this.newItem = new JMenuItem(StudentFileMenu.ITEM_NEW);
	this.newItem.setActionCommand(StudentFileMenu.COMMAND_NEW);
	this.newItem.addActionListener(this);
	add(this.newItem);
	
	this.saveItem = new JMenuItem(StudentFileMenu.ITEM_SAVE);
	this.saveItem.setActionCommand(StudentFileMenu.COMMAND_SAVE);
	this.saveItem.addActionListener(this);
	add(this.saveItem);
	
	this.deleteItem = new JMenuItem(StudentFileMenu.ITEM_DELETE);
	this.deleteItem.setActionCommand(StudentFileMenu.COMMAND_DELETE);
	this.deleteItem.addActionListener(this);
	add(this.deleteItem);
	
	add(new JSeparator());
	
	this.closeWindowItem = new JMenuItem(StudentFileMenu.ITEM_CLOSE_WINDOW);
	this.closeWindowItem.setActionCommand(StudentFileMenu.COMMAND_CLOSE_WINDOW);
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
    }

    public void deletedTestStatistics()
    {
    }

    public void deletedDirectedStatistics()
    {
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
	
	if (action.equals(StudentFileMenu.COMMAND_NEW))
	{
	    this.studentController.newStudent();
	    return;
	}
	
	if (action.equals(StudentFileMenu.COMMAND_SAVE))
	{
	    this.studentController.save();
	    return;
	}
	
	if (action.equals(StudentFileMenu.COMMAND_DELETE))
	{
	    StudentDeleteWindow studentDeleteWindow = new StudentDeleteWindow(this.studentController);
	    return;
	}
	
	if (action.equals(StudentFileMenu.COMMAND_CLOSE_WINDOW))
	{
	    this.studentController.closeView();
	    return;
	}
    }
}
