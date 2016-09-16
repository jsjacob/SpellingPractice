/*
 * AllStudentsReportWindow.java
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
import com.iamnota.SpellingPractice.Teacher.StudentStorage;
import com.iamnota.SpellingPractice.Teacher.TestStatisticsStorage;
import com.iamnota.SpellingPractice.Teacher.DirectedStatisticsStorage;
import com.iamnota.SpellingPractice.Teacher.WordStorage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.text.Element;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;

import java.util.Map;
import java.util.Set;
import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;
import java.util.Iterator;

public class AllStudentsReportWindow
    extends JFrame
{
    private static final String TITLE = "All Students Report - Teacher - SpellingPractice";

    private StudentController studentController;

    private AllStudentsReportPanel allStudentsReportPanel;

    protected static class AllStudentsReportPanel
	extends JPanel
    {
	private static final String STUDENT_COUNT_LABEL = "Student count: ";
	private static final String WORD_COUNT_LABEL = "Word count: ";

	private static final String STUDENT_LABEL = "Student";
	private static final String CORRECT_LABEL = "Correct";
	private static final String INCORRECT_LABEL = "Incorrect";
	private static final String COMPLETED_LABEL = "Completed";

	private static final int STUDENT_NAME_COLUMN = 1;
	private static final int CORRECT_COLUMN = 2;
	private static final int INCORRECT_COLUMN = 3;
	private static final int COMPLETED_COLUMN = 4;

	private StudentController studentController;

	private JEditorPane editorPane;

	private int wordCount;

	private Vector studentData;

	protected static class StudentDatum
	{
	    public String studentName;
	    public int correct;
	    public int incorrect;
	    public int completed;
	}

	public AllStudentsReportPanel(StudentController studentController)
	{
	    this.studentController = studentController;

	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	    WordStorage wordStorage = studentController.getWordController().getWordStorage();
	    Map idsToWords = wordStorage.getIdsToWords();
	    this.wordCount = idsToWords.size();

	    this.studentData = new Vector();

	    StudentModel studentModel = studentController.getReportStudentModel();
	    StudentStorage studentStorage = studentController.getReportStudentStorage();

	    Map idsToStudents = studentStorage.getIdsToStudents();
	    Set studentIds = idsToStudents.entrySet();
	    for (Iterator iterator = studentIds.iterator(); iterator.hasNext(); )
	    {
		Map.Entry me = (Map.Entry) iterator.next();

		String id = (String) me.getKey();
		String studentName = (String) me.getValue();

		StudentDatum studentDatum = new StudentDatum();
		studentDatum.studentName = studentName;

		// Get correct, incorrect, completed

		studentModel.setId(id);

		if (this.studentController.getPreferenceController().getModel().getIsShowTestingStatistics())
		{
		    TestStatisticsStorage testStatisticsStorage = studentController.getReportTestStatisticsStorage();
		    testStatisticsStorage.load();
		}

		if (this.studentController.getPreferenceController().getModel().getIsShowDirectedStatistics())
		{
		    DirectedStatisticsStorage directedStatisticsStorage = this.studentController.getReportDirectedStatisticsStorage();
		    directedStatisticsStorage.load();
		}

		studentDatum.correct = studentModel.getCorrectIdsToWords().size();
		studentDatum.incorrect = studentModel.getIncorrectIdsToWords().size();
		studentDatum.completed = (studentDatum.correct + studentDatum.incorrect);

		this.studentData.add(studentDatum);
	    }
	    
	    this.editorPane = new JEditorPane("text/html", "<html></html>");
	    this.editorPane.setEditable(false);

	    sortData(STUDENT_NAME_COLUMN, true);

	    JScrollPane scrollPane = new JScrollPane(this.editorPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setPreferredSize(new Dimension(400, 500));
	    add(scrollPane);
	}

	protected static class StudentDatumComparator
	    implements Comparator
	{
	    private int column;
	    private boolean isAscending;

	    public StudentDatumComparator(int column, boolean isAscending)
	    {
		this.column = column;
		this.isAscending = isAscending;
	    }

	    public int compare(Object o1, Object o2)
	    {
		StudentDatum sd1 = (StudentDatum) o1;
		StudentDatum sd2 = (StudentDatum) o2;

		int result = 0;

		switch (this.column)
		{
		case STUDENT_NAME_COLUMN:
		    result = (sd1.studentName.compareToIgnoreCase(sd2.studentName));
		    break;

		case CORRECT_COLUMN:
		    result = (new Integer(sd1.correct).compareTo(new Integer(sd2.correct)));
		    break;

		case INCORRECT_COLUMN:
		    result = (new Integer(sd1.incorrect).compareTo(new Integer(sd2.incorrect)));
		    break;

		case COMPLETED_COLUMN:
		    result = (new Integer(sd1.completed).compareTo(new Integer(sd2.completed)));
		    break;
		}

		if (!this.isAscending)
		{
		    result = -result;
		}

		return result;
	    }
	}

	protected void sortData(int column, boolean isAscending)
	{
	    Collections.sort(this.studentData, new StudentDatumComparator(column, isAscending));

	    String lineSeparator = System.getProperty("line.separator");

	    StringBuffer reportText = new StringBuffer();

	    reportText.append("<html>" + lineSeparator);

	    reportText.append("<head></head>" + lineSeparator);

	    reportText.append("<body>" + lineSeparator);

	    if (this.studentController.getPreferenceController().getModel().getIsShowTestingStatistics())
	    {
		reportText.append("<h1>Testing statistics</h1>" + lineSeparator);
	    }

	    if (this.studentController.getPreferenceController().getModel().getIsShowDirectedStatistics())
	    {
		reportText.append("<h1>Directed statistics</h1>" + lineSeparator);
	    }

	    reportText.append("<table><tr><th>Student count:</th><td>" + ("" + this.studentData.size()) + "</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><th>Word count:</th><td>" + ("" + this.wordCount) + "</td></tr></table>" + lineSeparator);

	    reportText.append("<br>" + lineSeparator);

	    reportText.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"4\">" + lineSeparator);
	    reportText.append("<tr><th>Student</th><th>Correct</th><th>Incorrect</th><th>Completed</th></tr>" + lineSeparator);
	    for (Iterator iterator = studentData.iterator(); iterator.hasNext(); )
	    {
		StudentDatum sd = (StudentDatum) iterator.next();
		reportText.append("<tr><td>" + sd.studentName + "</td><td>" + ("" + sd.correct) + "</td><td>" + ("" + sd.incorrect) + "</td><td>" + ("" + sd.completed) + "</td></tr>" + lineSeparator);
	    }	    
	    reportText.append("</table>" + lineSeparator);
	    
	    reportText.append("</body>" + lineSeparator);
	    reportText.append("</html>" + lineSeparator);

	    this.editorPane.setText(reportText.toString());
	    this.editorPane.setCaretPosition(0);
	}
    }

    public AllStudentsReportWindow(StudentController studentController)
    {
	super(TITLE);
	//    frame.setResizable(false);

	this.studentController = studentController;

	Container contentPane = getContentPane();
	this.allStudentsReportPanel = new AllStudentsReportPanel(this.studentController);
	contentPane.add(this.allStudentsReportPanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/2.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	show();
    }
}
