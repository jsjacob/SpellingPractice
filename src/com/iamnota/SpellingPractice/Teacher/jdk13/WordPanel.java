/*
 * WordPanel.java
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
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.WordStorage;
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

public class WordPanel
    extends JPanel
{
    private WordController wordController;
    private WordModel wordModel;

    private Vector idsToWordsEntries;

    private JList list;
    private JScrollPane scrollPane;
    private WordSettingsPanel wordSettingsPanel;

    private WordListSelectionListener wordListSelectionListener;

    protected class WordListSelectionListener
	implements ListSelectionListener
    {
	private WordController wordController;
	private JList list;
	private Vector idsToWordsEntries;

	public WordListSelectionListener(WordController wordController, JList list, Vector idsToWordsEntries)
	{
	    this.wordController = wordController;
	    this.list = list;
	    this.idsToWordsEntries = idsToWordsEntries;
	}

	public void valueChanged(ListSelectionEvent e)
	{
	    if (!e.getValueIsAdjusting())
	    {
		int wordIndex = this.list.getSelectedIndex();
		if (wordIndex != -1)
		{
		    Map.Entry me = (Map.Entry) this.idsToWordsEntries.get(wordIndex);
		    String id = (String) me.getKey();
		    this.wordController.load(id);
		}
	    }
	}
    }

    public WordPanel(WordController wordController, WordModel wordModel)
    {
	this.wordController = wordController;
	this.wordModel = wordModel;

	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

	PreferenceController preference = this.wordController.getPreferenceController();

	this.idsToWordsEntries = new Vector();

	this.list = new JList();
	this.wordListSelectionListener = new WordListSelectionListener(this.wordController, this.list, this.idsToWordsEntries);
	this.list.getSelectionModel().addListSelectionListener(this.wordListSelectionListener);

	this.scrollPane = new JScrollPane(this.list);
	this.scrollPane.setPreferredSize(new Dimension(150, 300));
	add(this.scrollPane);

	this.wordSettingsPanel = new WordSettingsPanel(this.wordController, this.wordModel);
	add(this.wordSettingsPanel);
    }

    public void openView()
    {
	loadWordList();
	this.list.setSelectedValue(this.wordModel.getWord(), true);

	this.wordSettingsPanel.openView();
    }

    public void closeView()
    {
	this.wordSettingsPanel.closeView();
    }

    public void updateModel()
    {
	this.wordSettingsPanel.updateModel();
    }

    public void opened()
    {
	this.list.setSelectedValue(this.wordModel.getWord(), true);

	this.wordSettingsPanel.opened();

    }

    public void closed()
    {
	this.wordSettingsPanel.closed();
    }

    public void modified()
    {
	this.wordSettingsPanel.modified();
    }

    public void saved()
    {
	loadWordList();
	this.list.setSelectedValue(this.wordModel.getWord(), true);

	this.wordSettingsPanel.saved();
    }

    public void deleted()
    {
	loadWordList();
    }

    public void recordStartedWord()
    {
	this.wordSettingsPanel.recordStartedWord();
    }

    public void recordStoppedWord()
    {
	this.wordSettingsPanel.recordStoppedWord();
    }

    public void playStartedWordNotModified()
    {
	this.wordSettingsPanel.playStartedWordNotModified();
    }

    public void playStoppedWordNotModified()
    {
	this.wordSettingsPanel.playStoppedWordNotModified();
    }

    public void playStartedWordModified()
    {
	this.wordSettingsPanel.playStartedWordModified();
    }

    public void playStoppedWordModified()
    {
	this.wordSettingsPanel.playStoppedWordModified();
    }

    public void showTestingStatistics()
    {
	this.wordSettingsPanel.showTestingStatistics();
    }

    public void showDirectedStatistics()
    {
	this.wordSettingsPanel.showDirectedStatistics();
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

    protected void loadWordList()
    {
	WordStorage wordStorage = this.wordController.getWordStorage();
	Map idsToWords = wordStorage.getIdsToWords();
	this.idsToWordsEntries.clear();
	this.idsToWordsEntries.addAll(idsToWords.entrySet());
	Collections.sort(this.idsToWordsEntries, new MapEntryValueComparator());
	String[] wordsArray = getValuesArray(this.idsToWordsEntries);
	this.list.setListData(wordsArray);
    }
}
