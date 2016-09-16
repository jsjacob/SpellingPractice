/*
 * StudentSettingsPanel.java
 *
 * Copyright (C) 2004 John S. Jacob <jsjacob@iamnota.com>
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
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Calendar;

public class StudentSettingsPanel
    extends JPanel
{
    private static final String INITIAL_NAME = "";

    private static final String NAME_LABEL = "Name: ";
    private static final String BIRTHDAY_LABEL = "Birthday: ";
    private static final String AUTO_LOGIN_LABEL = "Auto-login as this student";

    private static final String[] BIRTHDAY_MONTHS = { " - Month - ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
    private static final int[] BIRTHDAY_MONTHS_CODES = { -1, Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER };

    private static final String[] BIRTHDAY_DAYS = { " - Day - ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
    private static final int[] BIRTHDAY_DAYS_CODES = { -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 };

    private static final String LABEL_CLEAR_TEST_STATISTICS = "Clear test statistics...";
    private static final String COMMAND_CLEAR_TEST_STATISTICS = "CLEAR_TEST_STATISTICS";

    private static final String LABEL_CLEAR_DIRECTED_STATISTICS = "Clear directed statistics...";
    private static final String COMMAND_CLEAR_DIRECTED_STATISTICS = "CLEAR_DIRECTED_STATISTICS";

    private StudentController studentController;
    private StudentModel studentModel;

    private JTextField textField;
    private JComboBox birthdayMonthBox;
    private JComboBox birthdayDayBox;
    private JCheckBox isAutoLoginBox;
    private JButton clearTestStatisticsButton;
    private JButton clearDirectedStatisticsButton;

    private StudentSettingsDocumentListener studentSettingsDocumentListener;
    private StudentSettingsItemListener studentSettingsItemListener;
    private StudentSettingsActionListener studentSettingsActionListener;

    protected class StudentSettingsDocumentListener
	implements DocumentListener
    {
	private StudentController studentController;

	public StudentSettingsDocumentListener(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void insertUpdate(DocumentEvent e)
	{
	    this.studentController.modified();
	}

	public void removeUpdate(DocumentEvent e)
	{
	    this.studentController.modified();
	}

	public void changedUpdate(DocumentEvent e)
	{
	    this.studentController.modified();
	}
    }

    protected class StudentSettingsItemListener
	implements ItemListener
    {
	private StudentController studentController;

	public StudentSettingsItemListener(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void itemStateChanged(ItemEvent e)
	{
	    this.studentController.modified();
	}
    }

    protected class StudentSettingsActionListener
	implements ActionListener
    {
	private StudentController studentController;

	public StudentSettingsActionListener(StudentController studentController)
	{
	    this.studentController = studentController;
	}

	public void actionPerformed(ActionEvent e)
	{
	    String action = e.getActionCommand();
	    
	    if (action.equals(COMMAND_CLEAR_TEST_STATISTICS))
	    {
		StudentDeleteTestStatisticsWindow studentDeleteTestStatisticsWindow = new StudentDeleteTestStatisticsWindow(this.studentController);
		return;
	    }
	    
	    if (action.equals(COMMAND_CLEAR_DIRECTED_STATISTICS))
	    {
		StudentDeleteDirectedStatisticsWindow studentDeleteDirectedStatisticsWindow = new StudentDeleteDirectedStatisticsWindow(this.studentController);
		return;
	    }
	}
    }

    public StudentSettingsPanel(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;

	this.studentSettingsDocumentListener = new StudentSettingsDocumentListener(this.studentController);
	this.studentSettingsItemListener = new StudentSettingsItemListener(this.studentController);
	this.studentSettingsActionListener = new StudentSettingsActionListener(this.studentController);

	PreferenceController preference = this.studentController.getPreferenceController();

	GridBagLayout gbLayout = new GridBagLayout();
	setLayout(gbLayout);

	GridBagConstraints gbConstraints = new GridBagConstraints();
	gbConstraints.gridwidth = GridBagConstraints.REMAINDER;

	this.textField = new JTextField(INITIAL_NAME, 15);

        JPanel namePanel = new JPanel();
	JLabel nameLabel = new JLabel(NAME_LABEL);
        namePanel.add(nameLabel);
        namePanel.add(this.textField);

	gbLayout.setConstraints(namePanel, gbConstraints);
        add(namePanel);

	JPanel birthdayPanel = new JPanel();
	JLabel birthdayLabel = new JLabel(BIRTHDAY_LABEL);
        birthdayPanel.add(birthdayLabel);

	this.birthdayMonthBox = new JComboBox(BIRTHDAY_MONTHS);
	birthdayPanel.add(birthdayMonthBox);

	this.birthdayDayBox = new JComboBox(BIRTHDAY_DAYS);
	birthdayPanel.add(birthdayDayBox);

	gbLayout.setConstraints(birthdayPanel, gbConstraints);
	add(birthdayPanel);

        JPanel autoLoginPanel = new JPanel();
	this.isAutoLoginBox = new JCheckBox(AUTO_LOGIN_LABEL, false);
	autoLoginPanel.add(this.isAutoLoginBox);

	gbLayout.setConstraints(autoLoginPanel, gbConstraints);
	add(autoLoginPanel);

	JPanel clearStatisticsPanel = new JPanel();
	clearStatisticsPanel.setLayout(new BoxLayout(clearStatisticsPanel, BoxLayout.Y_AXIS));

	this.clearTestStatisticsButton = new JButton(LABEL_CLEAR_TEST_STATISTICS);
	this.clearTestStatisticsButton.setActionCommand(COMMAND_CLEAR_TEST_STATISTICS);
	this.clearTestStatisticsButton.addActionListener(this.studentSettingsActionListener);
	clearStatisticsPanel.add(this.clearTestStatisticsButton);

	this.clearDirectedStatisticsButton = new JButton(LABEL_CLEAR_DIRECTED_STATISTICS);
	this.clearDirectedStatisticsButton.setActionCommand(COMMAND_CLEAR_DIRECTED_STATISTICS);
	this.clearDirectedStatisticsButton.addActionListener(this.studentSettingsActionListener);
	clearStatisticsPanel.add(this.clearDirectedStatisticsButton);

	gbLayout.setConstraints(clearStatisticsPanel, gbConstraints);
	add(clearStatisticsPanel);
    }

    public void updateModel()
    {
	String nameText = this.textField.getText();
	this.studentModel.setName(nameText);
	
	int birthdayMonthIndex = this.birthdayMonthBox.getSelectedIndex();
	this.studentModel.setBirthdayMonth(BIRTHDAY_MONTHS_CODES[birthdayMonthIndex]);

	int birthdayDayIndex = this.birthdayDayBox.getSelectedIndex();
	this.studentModel.setBirthdayDay(BIRTHDAY_DAYS_CODES[birthdayDayIndex]);

	PreferenceController preference = this.studentController.getPreferenceController();
	boolean isAutoLogin = this.isAutoLoginBox.isSelected();
	if (isAutoLogin)
	{
	    preference.setStudentAutoLoginId(this.studentModel.getId());
	}
	else
	{
	    String studentAutoLoginId = preference.getStudentAutoLoginId();
	    if ((studentAutoLoginId != null) && studentAutoLoginId.equals(this.studentModel.getId()))
	    {
		preference.setStudentAutoLoginId(null);
	    }
	}
    }

    public void opened()
    {
	this.textField.setText(this.studentModel.getName());
	this.textField.setEnabled(true);
	this.textField.getDocument().addDocumentListener(this.studentSettingsDocumentListener);

	for (int i = 0; i < BIRTHDAY_MONTHS_CODES.length; i++)
	{
	    if (BIRTHDAY_MONTHS_CODES[i] == this.studentModel.getBirthdayMonth())
	    {
		this.birthdayMonthBox.setSelectedIndex(i);
	    }
	}
	this.birthdayMonthBox.setEnabled(true);
	this.birthdayMonthBox.addItemListener(this.studentSettingsItemListener);

	for (int i = 0; i < BIRTHDAY_DAYS_CODES.length; i++)
	{
	    if (BIRTHDAY_DAYS_CODES[i] == this.studentModel.getBirthdayDay())
	    {
		this.birthdayDayBox.setSelectedIndex(i);
	    }
	}
	this.birthdayDayBox.setEnabled(true);
	this.birthdayDayBox.addItemListener(this.studentSettingsItemListener);

	PreferenceController preference = this.studentController.getPreferenceController();
	String studentAutoLoginId = preference.getStudentAutoLoginId();
	boolean isAutoLogin = ((studentAutoLoginId != null) && this.studentModel.getId().equals(studentAutoLoginId));
	this.isAutoLoginBox.setSelected(isAutoLogin);
	this.isAutoLoginBox.setEnabled(true);
	this.isAutoLoginBox.addItemListener(this.studentSettingsItemListener);

	this.clearTestStatisticsButton.setEnabled(true);
	this.clearDirectedStatisticsButton.setEnabled(true);
    }

    public void closed()
    {
	this.textField.getDocument().removeDocumentListener(this.studentSettingsDocumentListener);
	this.textField.setText(INITIAL_NAME);
	this.textField.setEnabled(false);

	this.birthdayMonthBox.removeItemListener(this.studentSettingsItemListener);
	this.birthdayMonthBox.setSelectedIndex(0);
	this.birthdayMonthBox.setEnabled(false);

	this.birthdayDayBox.removeItemListener(this.studentSettingsItemListener);
	this.birthdayDayBox.setSelectedIndex(0);
	this.birthdayDayBox.setEnabled(false);

	this.isAutoLoginBox.removeItemListener(this.studentSettingsItemListener);
	this.isAutoLoginBox.setSelected(false);
	this.isAutoLoginBox.setEnabled(false);

	this.clearTestStatisticsButton.setEnabled(false);
	this.clearDirectedStatisticsButton.setEnabled(false);
    }

    public void modified()
    {
    }

    public void saved()
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
}
