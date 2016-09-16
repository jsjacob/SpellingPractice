/*
 * StudentStorageImpl.java
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

package com.iamnota.SpellingPractice.Teacher.jdk11;

import com.iamnota.SpellingPractice.Teacher.StudentStorage;
import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.StudentModel;
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;

public class StudentStorageImpl
    implements StudentStorage
{
    private StudentController studentController;
    private StudentModel studentModel;

    private static final String STUDENT_NAME = "name.txt";
    private static final String STUDENT_BIRTHDAY = "birthday.txt";
    private static final String AUTO_LOGIN = "autologin.txt";

    private static final String STUDENT_BIRTHDAY_FORMAT = "MM-dd";

    public class DirectoryFilter
	implements FileFilter
    {
	public boolean accept(File file)
	{
	    return file.isDirectory();
	}
    }

    public StudentStorageImpl(StudentController studentController, StudentModel studentModel)
    {
	this.studentController = studentController;
	this.studentModel = studentModel;
    }

    protected StudentController getStudentController()
    {
	return this.studentController;
    }

    protected StudentModel getStudentModel()
    {
	return this.studentModel;
    }

    public Map getIdsToStudents()
    {
	HashMap map = new HashMap();

	PreferenceController preference = this.studentController.getPreferenceController();
	File directory = new File(preference.getStudentStorageDirectory());

	File[] ids = directory.listFiles(new DirectoryFilter());
	if (ids != null)
	{
	    for (int i = 0; i < ids.length; i++)
	    {
		String id = ids[i].getName();
		
		try
		{
		    File studentDirectory = new File(directory.getPath() + File.separator + id);
		    FileReader studentNameFr = new FileReader(studentDirectory.getPath() + File.separator + STUDENT_NAME);
		    StringBuffer studentNameSb = new StringBuffer();
		    int bufferLength = 256;
		    char[] buffer = new char[bufferLength];
		    for (int charsRead = studentNameFr.read(buffer); charsRead != -1; charsRead = studentNameFr.read(buffer))
		    {
			studentNameSb.append(buffer, 0, charsRead);
		    }
		    
		    map.put(id, studentNameSb.toString());
		}
		
		catch (IOException e)
		{
		    // Ignore
		    ;
		}
	    }
	}

	return map;
    }

    public void load(String id)
	throws IOException
    {
	this.studentController.getLogView().debug("StudentStorageImpl.load()");

	String studentName = null;
	String studentBirthday = null;
	int birthdayMonth = -1;
	int birthdayDay = -1;

	PreferenceController preference = this.studentController.getPreferenceController();
	File studentDirectory = new File(preference.getStudentStorageDirectory() + File.separator + id);

	FileReader studentNameFr = new FileReader(studentDirectory.getPath() + File.separator + STUDENT_NAME);
	StringBuffer studentNameSb = new StringBuffer();
	int bufferLength = 256;
	char[] buffer = new char[bufferLength];
	for (int charsRead = studentNameFr.read(buffer); charsRead != -1; charsRead = studentNameFr.read(buffer))
	{
	    studentNameSb.append(buffer, 0, charsRead);
	}
	studentName = studentNameSb.toString();

	FileReader studentBirthdayFr = new FileReader(studentDirectory.getPath() + File.separator + STUDENT_BIRTHDAY);
	StringBuffer studentBirthdaySb = new StringBuffer();
	for (int charsRead = studentBirthdayFr.read(buffer); charsRead != -1; charsRead = studentBirthdayFr.read(buffer))
	{
	    studentBirthdaySb.append(buffer, 0, charsRead);
	}
	studentBirthday = studentBirthdaySb.toString();

	try
	{
	    SimpleDateFormat birthdayDateFormat = new SimpleDateFormat(STUDENT_BIRTHDAY_FORMAT);
	    Date birthdayDate = birthdayDateFormat.parse(studentBirthday);
	    Calendar birthdayCalendar = Calendar.getInstance();
	    birthdayCalendar.setTime(birthdayDate);
	    birthdayMonth = birthdayCalendar.get(Calendar.MONTH);
	    birthdayDay = birthdayCalendar.get(Calendar.DAY_OF_MONTH);
	}

	catch (ParseException e)
	{
	    throw new IOException("Couldn't parse student birthday");
	}

	this.studentModel.setId(id);
	this.studentModel.setName(studentName);
	this.studentModel.setBirthdayMonth(birthdayMonth);
	this.studentModel.setBirthdayDay(birthdayDay);
    }

    public void save()
	throws IOException
    {
	this.studentController.getLogView().debug("StudentStorageImpl.save()");

	PreferenceController preference = this.studentController.getPreferenceController();
	File directory = new File(preference.getStudentStorageDirectory() + File.separator + this.studentModel.getId());
	directory.mkdirs();

	FileWriter nameFw = new FileWriter(directory.getPath() + File.separator + STUDENT_NAME);
	nameFw.write(this.studentModel.getName());
	nameFw.flush();
	nameFw.close();

	Calendar birthdayCalendar = Calendar.getInstance();
	birthdayCalendar.set(Calendar.MONTH, this.studentModel.getBirthdayMonth());
	birthdayCalendar.set(Calendar.DAY_OF_MONTH, this.studentModel.getBirthdayDay());
	Date birthdayDate = birthdayCalendar.getTime();
	SimpleDateFormat birthdayDateFormat = new SimpleDateFormat(STUDENT_BIRTHDAY_FORMAT);
	String birthday = birthdayDateFormat.format(birthdayDate);

	FileWriter birthdayFw = new FileWriter(directory.getPath() + File.separator + STUDENT_BIRTHDAY);
	birthdayFw.write(birthday);
	birthdayFw.flush();
	birthdayFw.close();
    }

    public void delete()
	throws IOException
    {
	this.studentController.getLogView().debug("StudentStorageImpl.delete()");

	String id = this.studentModel.getId();
	if (id != null)
	{
	    PreferenceController preference = this.studentController.getPreferenceController();
	    File studentDirectory = new File(preference.getStudentStorageDirectory() + File.separator + id);
	    delete(studentDirectory);
	}
    }

    protected void delete(File file)
    {
	if (file.exists())
	{
	    if (file.isDirectory())
	    {
		String[] files =  file.list();
		if (files != null)
		{
		    for (int i = 0; i < files.length; i++)
		    {
			File childFile = new File(file.getPath() + File.separator + files[i]);
			delete(childFile);
		    }
		}
	    }

	    file.delete();
	}
    }
}
