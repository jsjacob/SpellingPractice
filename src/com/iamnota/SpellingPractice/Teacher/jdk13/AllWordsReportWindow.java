/*
 * AllWordsReportWindow.java
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

import com.iamnota.SpellingPractice.Teacher.WordController;
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.WordStorage;
import com.iamnota.SpellingPractice.Teacher.StudentController;
import com.iamnota.SpellingPractice.Teacher.StudentModel;
import com.iamnota.SpellingPractice.Teacher.StudentStorage;
import com.iamnota.SpellingPractice.Teacher.TestStatisticsStorage;
import com.iamnota.SpellingPractice.Teacher.DirectedStatisticsStorage;

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
import java.util.HashMap;
import java.util.Set;
import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;
import java.util.Iterator;

public class AllWordsReportWindow
    extends JFrame
{
    private static final String TITLE = "All Words Report - Teacher - SpellingPractice";

    private WordController wordController;

    private AllWordsReportPanel allWordsReportPanel;

    protected static class AllWordsReportPanel
	extends JPanel
    {
	private static final String WORD_COUNT_LABEL = "Word count: ";
	private static final String STUDENT_COUNT_LABEL = "Student count: ";

	private static final String WORD_LABEL = "Word";
	private static final String CORRECT_LABEL = "Correct";
	private static final String INCORRECT_LABEL = "Incorrect";
	private static final String COMPLETED_LABEL = "Completed";

	private static final int WORD_NAME_COLUMN = 1;
	private static final int CORRECT_COLUMN = 2;
	private static final int INCORRECT_COLUMN = 3;
	private static final int COMPLETED_COLUMN = 4;

	private WordController wordController;

	private JEditorPane editorPane;

	private int studentCount;

	private Vector wordData;

	protected static class WordDatum
	{
	    public String wordName;
	    public int correct;
	    public int incorrect;
	    public int completed;
	}

	public AllWordsReportPanel(WordController wordController)
	{
	    this.wordController = wordController;

	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	    Map wordDataMap = new HashMap();

	    WordStorage wordStorage = this.wordController.getWordStorage();
	    Map idsToWords = wordStorage.getIdsToWords();
	    Set wordIds = idsToWords.entrySet();
	    for (Iterator iterator = wordIds.iterator(); iterator.hasNext(); )
	    {
		Map.Entry me = (Map.Entry) iterator.next();

		String id = (String) me.getKey();
		String wordName = (String) me.getValue();

		WordDatum wordDatum = new WordDatum();
		wordDatum.wordName = wordName;
		wordDatum.correct = 0;
		wordDatum.incorrect = 0;
		wordDatum.completed = 0;

		wordDataMap.put(id, wordDatum);
	    }
	    
	    // Get correct, incorrect, completed
	    StudentController studentController = this.wordController.getStudentController();
	    StudentStorage studentStorage = studentController.getReportStudentStorage();
	    StudentModel studentModel = studentController.getReportStudentModel();

	    Map idsToStudents = studentStorage.getIdsToStudents();
	    this.studentCount = idsToStudents.size();
	    Set studentIds = idsToStudents.keySet();
	    for (Iterator iterator = studentIds.iterator(); iterator.hasNext(); )
	    {
		String id = (String) iterator.next();

		studentModel.setId(id);

		if (this.wordController.getPreferenceController().getModel().getIsShowTestingStatistics())
		{
		    TestStatisticsStorage testStatisticsStorage = studentController.getReportTestStatisticsStorage();
		    testStatisticsStorage.load();
		}

		if (this.wordController.getPreferenceController().getModel().getIsShowDirectedStatistics())
		{
		    DirectedStatisticsStorage directedStatisticsStorage = studentController.getReportDirectedStatisticsStorage();
		    directedStatisticsStorage.load();
		}

		Set correctIds = studentModel.getCorrectIdsToWords().keySet();
		for (Iterator correctIdIter = correctIds.iterator(); correctIdIter.hasNext(); )
		{
		    String correctId = (String) correctIdIter.next();

		    WordDatum wd = (WordDatum) wordDataMap.get(correctId);
		    if (wd != null)
		    {
			wd.correct++;
			wd.completed++;
		    }
		}

		Set incorrectIds = studentModel.getIncorrectIdsToWords().keySet();
		for (Iterator incorrectIdIter = incorrectIds.iterator(); incorrectIdIter.hasNext(); )
		{
		    String incorrectId = (String) incorrectIdIter.next();

		    WordDatum wd = (WordDatum) wordDataMap.get(incorrectId);
		    if (wd != null)
		    {
			wd.incorrect++;
			wd.completed++;
		    }
		}
	    }

	    this.wordData = new Vector(wordDataMap.values());

	    this.editorPane = new JEditorPane("text/html", "<html></html>");
	    this.editorPane.setEditable(false);

	    sortData(WORD_NAME_COLUMN, true);

	    JScrollPane scrollPane = new JScrollPane(this.editorPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setPreferredSize(new Dimension(400, 500));
	    add(scrollPane);
	}

	protected static class WordDatumComparator
	    implements Comparator
	{
	    private int column;
	    private boolean isAscending;

	    public WordDatumComparator(int column, boolean isAscending)
	    {
		this.column = column;
		this.isAscending = isAscending;
	    }

	    public int compare(Object o1, Object o2)
	    {
		WordDatum wd1 = (WordDatum) o1;
		WordDatum wd2 = (WordDatum) o2;

		int result = 0;

		switch (this.column)
		{
		case WORD_NAME_COLUMN:
		    result = (wd1.wordName.compareToIgnoreCase(wd2.wordName));
		    break;

		case CORRECT_COLUMN:
		    result = (new Integer(wd1.correct).compareTo(new Integer(wd2.correct)));
		    break;

		case INCORRECT_COLUMN:
		    result = (new Integer(wd1.incorrect).compareTo(new Integer(wd2.incorrect)));
		    break;

		case COMPLETED_COLUMN:
		    result = (new Integer(wd1.completed).compareTo(new Integer(wd2.completed)));
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
	    Collections.sort(this.wordData, new WordDatumComparator(column, isAscending));

	    String lineSeparator = System.getProperty("line.separator");

	    StringBuffer reportText = new StringBuffer();

	    reportText.append("<html>" + lineSeparator);

	    reportText.append("<head></head>" + lineSeparator);

	    reportText.append("<body>" + lineSeparator);

	    if (this.wordController.getPreferenceController().getModel().getIsShowTestingStatistics())
	    {
		reportText.append("<h1>Testing statistics</h1>" + lineSeparator);
	    }

	    if (this.wordController.getPreferenceController().getModel().getIsShowDirectedStatistics())
	    {
		reportText.append("<h1>Directed statistics</h1>" + lineSeparator);
	    }

	    reportText.append("<table><tr><th>Word count:</th><td>" + ("" + this.wordData.size()) + "</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><th>Student count:</th><td>" + ("" + this.studentCount) + "</td></tr></table>" + lineSeparator);

	    reportText.append("<br>" + lineSeparator);

	    reportText.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"4\">" + lineSeparator);
	    reportText.append("<tr><th>Word</th><th>Correct</th><th>Incorrect</th><th>Completed</th></tr>" + lineSeparator);
	    for (Iterator iterator = wordData.iterator(); iterator.hasNext(); )
	    {
		WordDatum wd = (WordDatum) iterator.next();
		reportText.append("<tr><td>" + wd.wordName + "</td><td>" + ("" + wd.correct) + "</td><td>" + ("" + wd.incorrect) + "</td><td>" + ("" + wd.completed) + "</td></tr>" + lineSeparator);
	    }	    
	    reportText.append("</table>" + lineSeparator);
	    
	    reportText.append("</body>" + lineSeparator);
	    reportText.append("</html>" + lineSeparator);

	    this.editorPane.setText(reportText.toString());
	    this.editorPane.setCaretPosition(0);
	}
    }

    public AllWordsReportWindow(WordController wordController)
    {
	super(TITLE);
	//    frame.setResizable(false);

	this.wordController = wordController;

	Container contentPane = getContentPane();
	this.allWordsReportPanel = new AllWordsReportPanel(this.wordController);
	contentPane.add(this.allWordsReportPanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/2.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	show();
    }
}
