/*
 * StudentPanel.java
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
import com.iamnota.SpellingPractice.Teacher.StudentModel;
import com.iamnota.SpellingPractice.Teacher.StudentStorage;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Dimension;

import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;
import java.util.Iterator;

public class StudentPanel
    extends JPanel
{
    private static final String SUMMARY_TITLE = "Summary";
    private static final String SUMMARY_TOOLTIP = "Word totals";
    private static final String INCORRECT_TITLE = "Incorrect";
    private static final String INCORRECT_TOOLTIP = "Words spelled incorrectly";
    private static final String CORRECT_TITLE = "Correct";
    private static final String CORRECT_TOOLTIP = "Words spelled correctly";
    private static final String SETTINGS_TITLE = "Settings";
    private static final String SETTINGS_TOOLTIP = "Edit student settings";

    private StudentController studentController;
    private StudentModel studentModel;

    private Vector idsToStudentsEntries;

    private JList list;
    private JScrollPane scrollPane;
    private JTabbedPane tabbedPane;

    private StudentSummaryPanel studentSummaryPanel;
    private StudentIncorrectPanel studentIncorrectPanel;
    private StudentCorrectPanel studentCorrectPanel;
    private StudentSettingsPanel studentSettingsPanel;

    private StudentListSelectionListener studentListSelectionListener;

    protected class StudentListSelectionListener
	implements ListSelectionListener
    {
	private StudentController studentController;
	private JList list;
	private Vector idsToStudentsEntries;

	public StudentListSelectionListener(StudentController studentController, JList list, Vector idsToStudentsEntries)
	{
	    this.studentController = studentController;
	    this.list = list;
	    this.idsToStudentsEntries = idsToStudentsEntries;
	}

	public void valueChanged(ListSelectionEvent e)
	{
	    if (!e.getValueIsAdjusting())
	    {
		int studentIndex = this.list.getSelectedIndex();
		if (studentIndex != -1)
		{
		    Map.Entry me = (Map.Entry) this.idsToStudentsEntries.get(studentIndex);
		    String id = (String) me.getKey();
		    this.studentController.load(id);
		}
	    }
	}
    }

    public StudentPanel(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;

	PreferenceController preference = this.studentController.getPreferenceController();

	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

	this.idsToStudentsEntries = new Vector();

	this.list = new JList();
	this.studentListSelectionListener = new StudentListSelectionListener(this.studentController, this.list, this.idsToStudentsEntries);
	this.list.getSelectionModel().addListSelectionListener(this.studentListSelectionListener);

	this.scrollPane = new JScrollPane(this.list);
	this.scrollPane.setPreferredSize(new Dimension(200, 300));
	add(this.scrollPane);

	this.tabbedPane = new JTabbedPane();
	this.tabbedPane.setPreferredSize(new Dimension(400, 300));

	this.studentSummaryPanel = new StudentSummaryPanel(this.studentController, this.studentModel);
	this.tabbedPane.addTab(SUMMARY_TITLE, null, this.studentSummaryPanel, SUMMARY_TOOLTIP);

	this.studentIncorrectPanel = new StudentIncorrectPanel(this.studentController, this.studentModel);
	this.tabbedPane.addTab(INCORRECT_TITLE, null, this.studentIncorrectPanel, INCORRECT_TOOLTIP);

	this.studentCorrectPanel = new StudentCorrectPanel(this.studentController, this.studentModel);
	this.tabbedPane.addTab(CORRECT_TITLE, null, this.studentCorrectPanel, CORRECT_TOOLTIP);

	this.studentSettingsPanel = new StudentSettingsPanel(this.studentController, this.studentModel);
	this.tabbedPane.addTab(SETTINGS_TITLE, null, this.studentSettingsPanel, SETTINGS_TOOLTIP);

	add(this.tabbedPane);
    }

    public void openView()
    {
	loadStudentList();
	this.list.setSelectedValue(this.studentModel.getName(), true);
    }

    public void closeView()
    {
    }

    public void updateModel()
    {
	this.studentSummaryPanel.updateModel();
	this.studentIncorrectPanel.updateModel();
	this.studentCorrectPanel.updateModel();
	this.studentSettingsPanel.updateModel();
    }

    public void opened()
    {
	this.list.setSelectedValue(this.studentModel.getName(), true);

	this.studentSummaryPanel.opened();
	this.studentIncorrectPanel.opened();
	this.studentCorrectPanel.opened();
	this.studentSettingsPanel.opened();
    }

    public void closed()
    {
	this.studentSummaryPanel.closed();
	this.studentIncorrectPanel.closed();
	this.studentCorrectPanel.closed();
	this.studentSettingsPanel.closed();
    }

    public void modified()
    {
	this.studentSummaryPanel.modified();
	this.studentIncorrectPanel.modified();
	this.studentCorrectPanel.modified();
	this.studentSettingsPanel.modified();
    }

    public void saved()
    {
	loadStudentList();
	this.list.setSelectedValue(this.studentModel.getName(), true);

	this.studentSummaryPanel.saved();
	this.studentIncorrectPanel.saved();
	this.studentCorrectPanel.saved();
	this.studentSettingsPanel.saved();
    }

    public void deleted()
    {
	loadStudentList();
    }

    public void deletedTestStatistics()
    {
	if (this.studentModel.getId() != null)
	{
	    this.studentSummaryPanel.deletedTestStatistics();
	    this.studentIncorrectPanel.deletedTestStatistics();
	    this.studentCorrectPanel.deletedTestStatistics();
	    this.studentSettingsPanel.deletedTestStatistics();
	}
    }

    public void deletedDirectedStatistics()
    {
	if (this.studentModel.getId() != null)
	{
	    this.studentSummaryPanel.deletedDirectedStatistics();
	    this.studentIncorrectPanel.deletedDirectedStatistics();
	    this.studentCorrectPanel.deletedDirectedStatistics();
	    this.studentSettingsPanel.deletedDirectedStatistics();
	}
    }

    public void showTestingStatistics()
    {
	if (this.studentModel.getId() != null)
	{
	    this.studentSummaryPanel.showTestingStatistics();
	    this.studentIncorrectPanel.showTestingStatistics();
	    this.studentCorrectPanel.showTestingStatistics();
	    this.studentSettingsPanel.showTestingStatistics();
	}
    }

    public void showDirectedStatistics()
    {
	if (this.studentModel.getId() != null)
	{
	    this.studentSummaryPanel.showDirectedStatistics();
	    this.studentIncorrectPanel.showDirectedStatistics();
	    this.studentCorrectPanel.showDirectedStatistics();
	    this.studentSettingsPanel.showDirectedStatistics();
	}
    }

    protected static String[] getValuesArray(List mapEntries)
    {
	Vector values = new Vector();

	for (Iterator iterator = mapEntries.iterator(); iterator.hasNext(); )
	{
	    Map.Entry me = (Map.Entry) iterator.next();
	    values.add(me.getValue());
	}

	return (String[]) values.toArray(new String[] {});
    }

    protected static class MapEntryValueComparator
	implements Comparator
    {
	public int compare(Object o1, Object o2)
	{
	    Map.Entry me1 = (Map.Entry) o1;
	    Map.Entry me2 = (Map.Entry) o2;

	    return ((String) me1.getValue()).compareToIgnoreCase((String) me2.getValue());
	}
    }

    protected void loadStudentList()
    {
	StudentStorage studentStorage = this.studentController.getStudentStorage();
	Map idsToStudents = studentStorage.getIdsToStudents();
	this.idsToStudentsEntries.clear();
	this.idsToStudentsEntries.addAll(idsToStudents.entrySet());
	Collections.sort(this.idsToStudentsEntries, new MapEntryValueComparator());
	String[] studentsArray = getValuesArray(this.idsToStudentsEntries);
	this.list.setListData(studentsArray);
    }
}
