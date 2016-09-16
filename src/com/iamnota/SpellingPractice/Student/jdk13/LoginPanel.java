/*
 * LoginPanel.java
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

import com.iamnota.SpellingPractice.Student.Controller;
import com.iamnota.SpellingPractice.Student.LoginModel;
import com.iamnota.SpellingPractice.Student.LoginView;
import com.iamnota.SpellingPractice.Student.StudentStorage;
import com.iamnota.SpellingPractice.Student.PreferenceModel;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Calendar;

import java.util.Map;
import java.util.List;
import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;
import java.util.Iterator;

public class LoginPanel
    extends JPanel
    implements ActionListener
{
    private static final String INCOMPLETE_LOGIN = "Please select a name, a birthday month and a birthday day.";

    private static final String COMMAND_START = "START";
    private static final String START_LABEL = "Start";

    private static final String COMMAND_ABOUT = "ABOUT";
    private static final String ABOUT_LABEL = "About";

    private static final String INITIAL_NAME = "";
    private static final String INITIAL_LOGIN_STATUS = "";

    private static final String NAME_TITLE = "Name";
    private static final String BIRTHDAY_TITLE = "Birthday";

    private static final String[] BIRTHDAY_MONTHS = { " - Month - ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
    private static final int[] BIRTHDAY_MONTHS_CODES = { -1, Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER };

    private static final String[] BIRTHDAY_DAYS = { " - Day - ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
    private static final int[] BIRTHDAY_DAYS_CODES = { -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

    private JList list;
    private JScrollPane listScrollPane;
    private JComboBox birthdayMonthBox;
    private JComboBox birthdayDayBox;

    private Controller controller;
    private LoginModel loginModel;

    private Vector idsToStudentsEntries;

    public LoginPanel(Controller controller, LoginModel loginModel)
    {
	this.controller = controller;
	this.loginModel = loginModel;

	PreferenceModel preferenceModel = this.controller.getPreferenceModel();

	GridBagLayout gbl = new GridBagLayout();
	setLayout(gbl);

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = GridBagConstraints.RELATIVE;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = GridBagConstraints.REMAINDER;
	gbc.fill = GridBagConstraints.NONE;

	StudentStorage studentStorage = controller.getStudentStorage();
	Map idsToStudents = studentStorage.getIdsToStudents();
	this.idsToStudentsEntries = new Vector(idsToStudents.entrySet());
	Collections.sort(this.idsToStudentsEntries, new MapEntryValueComparator());
	String[] studentsArray = getValuesArray(this.idsToStudentsEntries);

	this.list = new JList(studentsArray);
	//        this.list.setPreferredSize(new Dimension(140,25));

	this.listScrollPane = new JScrollPane(this.list);
	this.listScrollPane.setPreferredSize(new Dimension(200, 300));

        JPanel namePanel = new JPanel();
	namePanel.setBorder(BorderFactory.createTitledBorder(NAME_TITLE));
	namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        namePanel.add(this.listScrollPane);
	gbc.gridwidth = 1;
	gbl.setConstraints(namePanel, gbc);
        add(namePanel);

	JPanel rightPanel = new JPanel();
	rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));


	JPanel birthdayPanel = new JPanel();
	birthdayPanel.setBorder(BorderFactory.createTitledBorder(BIRTHDAY_TITLE));
	birthdayPanel.setLayout(new BoxLayout(birthdayPanel, BoxLayout.Y_AXIS));

	JPanel birthdayInputPanel = new JPanel();
	birthdayInputPanel.setLayout(new BoxLayout(birthdayInputPanel, BoxLayout.X_AXIS));

	this.birthdayMonthBox = new JComboBox(BIRTHDAY_MONTHS);
	birthdayInputPanel.add(birthdayMonthBox);

	this.birthdayDayBox = new JComboBox(BIRTHDAY_DAYS);
	birthdayInputPanel.add(birthdayDayBox);

	birthdayPanel.add(birthdayInputPanel);

	rightPanel.add(birthdayPanel);

        JPanel buttonPanel = new JPanel();

	JButton startButton = new JButton(START_LABEL);
	startButton.setActionCommand(COMMAND_START);
	startButton.addActionListener(this);
	buttonPanel.add(startButton);

	JButton aboutButton = new JButton(ABOUT_LABEL);
	aboutButton.setActionCommand(COMMAND_ABOUT);
	aboutButton.addActionListener(this);
	buttonPanel.add(aboutButton);

	rightPanel.add(buttonPanel);

	gbc.gridwidth = GridBagConstraints.REMAINDER;
	gbl.setConstraints(rightPanel, gbc);
	add(rightPanel);
    }

    public void clear()
    {
	this.list.setSelectedIndex(0);
	this.birthdayMonthBox.setSelectedIndex(0);
	this.birthdayDayBox.setSelectedIndex(0);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(COMMAND_START))
	{
	    int nameIndex = this.list.getSelectedIndex();
	    int birthdayMonthIndex = this.birthdayMonthBox.getSelectedIndex();
	    int birthdayDayIndex = this.birthdayDayBox.getSelectedIndex();

	    if ((nameIndex != -1) &&
		(birthdayMonthIndex != 0) &&
		(birthdayDayIndex != 0))
	    {
		String nameText = (String) this.list.getSelectedValue();
		Map.Entry studentMe = (Map.Entry) this.idsToStudentsEntries.get(nameIndex);
		String studentId = (String) studentMe.getKey();
		this.loginModel.setId(studentId);
		this.loginModel.setName(nameText);
		
		this.loginModel.setBirthdayMonth(BIRTHDAY_MONTHS_CODES[birthdayMonthIndex]);
		
		this.loginModel.setBirthdayDay(BIRTHDAY_DAYS_CODES[birthdayDayIndex]);

		this.controller.login();
	    }
	    else
	    {
		LoginView loginView = this.controller.getLoginView();
		loginView.displayMessage(INCOMPLETE_LOGIN);
	    }

	    return;
	}

	if (action.equals(COMMAND_ABOUT))
	{
	    this.controller.doAbout();
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
}
