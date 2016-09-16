/*
 * WordReportMenu.java
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
import com.iamnota.SpellingPractice.Teacher.PreferenceModel;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordReportMenu
  extends JMenu
  implements ActionListener
{
    private static final String LABEL = "Report";

    private static final String ITEM_SHOW_TESTING_STATISTICS = "Show testing statistics";
    private static final String COMMAND_SHOW_TESTING_STATISTICS = "SHOW_TESTING_STATISTICS";

    private static final String ITEM_SHOW_DIRECTED_STATISTICS = "Show directed statistics";
    private static final String COMMAND_SHOW_DIRECTED_STATISTICS = "SHOW_DIRECTED_STATISTICS";

    private static final String ITEM_ALL_WORDS = "All words";
    private static final String COMMAND_ALL_WORDS = "ALL_WORDS";
    
    private WordController wordController;

    private JRadioButtonMenuItem showTestingStatisticsItem;
    private JRadioButtonMenuItem showDirectedStatisticsItem;

    private JMenuItem allWordsItem;

    public WordReportMenu(WordController wordController)
    {
	super(WordReportMenu.LABEL);
	
	this.wordController = wordController;
	
	this.showTestingStatisticsItem = new JRadioButtonMenuItem(WordReportMenu.ITEM_SHOW_TESTING_STATISTICS);
	this.showTestingStatisticsItem.setActionCommand(WordReportMenu.COMMAND_SHOW_TESTING_STATISTICS);
	this.showTestingStatisticsItem.addActionListener(this);
	add(this.showTestingStatisticsItem);

	this.showDirectedStatisticsItem = new JRadioButtonMenuItem(WordReportMenu.ITEM_SHOW_DIRECTED_STATISTICS);
	this.showDirectedStatisticsItem.setActionCommand(WordReportMenu.COMMAND_SHOW_DIRECTED_STATISTICS);
	this.showDirectedStatisticsItem.addActionListener(this);
	add(this.showDirectedStatisticsItem);

	add(new JSeparator());

	this.allWordsItem = new JMenuItem(WordReportMenu.ITEM_ALL_WORDS);
	this.allWordsItem.setActionCommand(WordReportMenu.COMMAND_ALL_WORDS);
	this.allWordsItem.addActionListener(this);
	add(this.allWordsItem);

	this.showTestingStatisticsItem.setEnabled(true);
	this.showDirectedStatisticsItem.setEnabled(true);

	this.allWordsItem.setEnabled(true);
    }

    public void openView()
    {
	PreferenceModel preferenceModel = this.wordController.getPreferenceController().getModel();

	this.showTestingStatisticsItem.setEnabled(true);
	this.showTestingStatisticsItem.setSelected(preferenceModel.getIsShowTestingStatistics());
	this.showDirectedStatisticsItem.setEnabled(true);
	this.showDirectedStatisticsItem.setSelected(preferenceModel.getIsShowDirectedStatistics());

	this.showTestingStatisticsItem.setEnabled(true);
	this.showDirectedStatisticsItem.setEnabled(true);
    }

    public void closeView()
    {
    }

    public void opened()
    {
    }

    public void closed()
    {
    }

    public void modified()
    {
    }

    public void saved()
    {
    }

    public void deleted()
    {
    }

    public void recordStartedWord()
    {
	this.allWordsItem.setEnabled(false);
    }

    public void recordStoppedWord()
    {
	this.allWordsItem.setEnabled(true);
    }

    public void playStartedWordNotModified()
    {
	this.allWordsItem.setEnabled(false);
    }

    public void playStoppedWordNotModified()
    {
	this.allWordsItem.setEnabled(true);
    }
    
    public void playStartedWordModified()
    {
	this.allWordsItem.setEnabled(false);
    }

    public void playStoppedWordModified()
    {
	this.allWordsItem.setEnabled(true);
    }
    
    public void showTestingStatistics()
    {
	this.showTestingStatisticsItem.setSelected(true);
	this.showDirectedStatisticsItem.setSelected(false);
    }

    public void showDirectedStatistics()
    {
	this.showTestingStatisticsItem.setSelected(false);
	this.showDirectedStatisticsItem.setSelected(true);
    }

    public void actionPerformed(ActionEvent e)
    {
	String action = e.getActionCommand();
	
	if (action.equals(WordReportMenu.COMMAND_SHOW_TESTING_STATISTICS))
	{
	    this.wordController.getPreferenceController().showTestingStatistics();
	    return;
	}
	
	if (action.equals(WordReportMenu.COMMAND_SHOW_DIRECTED_STATISTICS))
	{
	    this.wordController.getPreferenceController().showDirectedStatistics();
	    return;
	}

	if (action.equals(WordReportMenu.COMMAND_ALL_WORDS))
	{
	    this.wordController.displayAllWordsReport();
	    return;
	}
    }
}
