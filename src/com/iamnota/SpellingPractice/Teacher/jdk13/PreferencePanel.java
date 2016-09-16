/*
 * PreferencePanel.java
 *
 * Copyright (C) 2003-2004 John S. Jacob <jsjacob@iamnota.com>
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

import com.iamnota.SpellingPractice.Teacher.PreferenceController;
import com.iamnota.SpellingPractice.Teacher.PreferenceModel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class PreferencePanel
    extends JPanel
{
    private static final String INITIAL_MODE = "directed";

    private static final String MODE_LABEL = "Mode: ";
    private static final String[] MODES = { "Random", "Directed", "Test", "Test and Directed" };
    private static final int[] MODE_CODES = { PreferenceModel.MODE_RANDOM, PreferenceModel.MODE_DIRECTED, PreferenceModel.MODE_TEST, PreferenceModel.MODE_TEST_AND_DIRECTED };

    private JComboBox modeBox;

    private PreferenceController preference;
    private PreferenceModel preferenceModel;

    private PreferenceDocumentListener preferenceDocumentListener;
    private PreferenceItemListener preferenceItemListener;

    protected class PreferenceItemListener
	implements ItemListener
    {
	private PreferenceController preference;

	public PreferenceItemListener(PreferenceController preference)
	{
	    this.preference = preference;
	}

	public void itemStateChanged(ItemEvent e)
	{
	    this.preference.modified();
	}
    }

    protected class PreferenceDocumentListener
	implements DocumentListener
    {
	private PreferenceController preference;

	public PreferenceDocumentListener(PreferenceController preference)
	{
	    this.preference = preference;
	}

	public void insertUpdate(DocumentEvent e)
	{
	    this.preference.modified();
	}

	public void removeUpdate(DocumentEvent e)
	{
	    this.preference.modified();
	}

	public void changedUpdate(DocumentEvent e)
	{
	    this.preference.modified();
	}
    }

    public PreferencePanel(PreferenceController preference, PreferenceModel preferenceModel)
    {
	this.preference = preference;
	this.preferenceModel = preferenceModel;

	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	this.preferenceDocumentListener = new PreferenceDocumentListener(this.preference);

	this.preferenceItemListener = new PreferenceItemListener(this.preference);

	JPanel modePanel = new JPanel();
	modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.X_AXIS));

	JLabel modeLabel = new JLabel(MODE_LABEL);
        modePanel.add(modeLabel);

	this.modeBox = new JComboBox(MODES);
	modePanel.add(modeBox);

	add(modePanel);
    }

    public void updateModel()
    {
	int modeIndex = this.modeBox.getSelectedIndex();
	this.preferenceModel.setMode(MODE_CODES[modeIndex]);
    }

    public void opened()
    {
	for (int i = 0; i < MODE_CODES.length; i++)
	{
	    if (MODE_CODES[i] == this.preferenceModel.getMode())
	    {
		this.modeBox.setSelectedIndex(i);
	    }
	}
	this.modeBox.setEnabled(true);
	this.modeBox.addItemListener(this.preferenceItemListener);
    }

    public void closed()
    {
	this.modeBox.removeItemListener(this.preferenceItemListener);
	this.modeBox.setSelectedIndex(0);
	this.modeBox.setEnabled(false);
    }

    public void modified()
    {
    }

    public void saved()
    {
    }
}
