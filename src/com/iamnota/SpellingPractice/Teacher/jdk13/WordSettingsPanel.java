/*
 * WordSettingsPanel.java
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
import com.iamnota.SpellingPractice.Teacher.PreferenceController;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WordSettingsPanel
    extends JPanel
{
    private static final String INITIAL_WORD = "";
    private static final String WORD_LABEL = "Word: ";

    private static final String LABEL_NEW = "New";
    private static final String COMMAND_NEW = "NEW";
    
    private static final String LABEL_OPEN = "Open...";
    private static final String COMMAND_OPEN = "OPEN";
    
    private static final String LABEL_SAVE = "Save";
    private static final String COMMAND_SAVE = "SAVE";
    
    private static final String LABEL_DELETE = "Delete";
    private static final String COMMAND_DELETE = "DELETE";

    private static final String TITLE_WORD_AUDIO = "Word audio";
    private static final String TITLE_SENTENCE_AUDIO = "Sentence audio";
    
    private static final String LABEL_WORD_RECORD = "Record";
    private static final String COMMAND_WORD_RECORD = "RECORD_WORD";
    
    private static final String LABEL_WORD_PLAY = "Play";
    private static final String COMMAND_WORD_PLAY = "PLAY_WORD";
    
    private static final String LABEL_WORD_STOP = "Stop";
    private static final String COMMAND_WORD_STOP = "STOP_WORD";
    
    private JTextField textField;
    private JButton wordAudioPlayButton;
    private JButton wordAudioRecordButton;
    private JButton wordAudioStopButton;

    private WordController wordController;
    private WordModel wordModel;

    private WordSettingsActionListener wordSettingsActionListener;
    private WordSettingsDocumentListener wordSettingsDocumentListener;

    protected class WordSettingsActionListener
	implements ActionListener
    {
	private WordController wordController;

	public WordSettingsActionListener(WordController wordController)
	{
	    this.wordController = wordController;
	}

	public void actionPerformed(ActionEvent e)
	{
	    String action = e.getActionCommand();
	    
	    if (action.equals(COMMAND_WORD_PLAY))
	    {
		this.wordController.playStartWord();
		return;
	    }
	    
	    if (action.equals(COMMAND_WORD_RECORD))
	    {
		this.wordController.recordStartWord();
		return;
	    }
	
	    if (action.equals(COMMAND_WORD_STOP))
	    {
		this.wordController.playStopWord();
		this.wordController.recordStopWord();
		return;
	    }
	}
    }

    protected class WordSettingsDocumentListener
	implements DocumentListener
    {
	private WordController wordController;

	public WordSettingsDocumentListener(WordController wordController)
	{
	    this.wordController = wordController;
	}

	public void insertUpdate(DocumentEvent e)
	{
	    this.wordController.modified();
	}

	public void removeUpdate(DocumentEvent e)
	{
	    this.wordController.modified();
	}

	public void changedUpdate(DocumentEvent e)
	{
	    this.wordController.modified();
	}
    }

    public WordSettingsPanel(WordController wordController, WordModel wordModel)
    {
	this.wordController = wordController;
	this.wordModel = wordModel;

	PreferenceController preference = this.wordController.getPreferenceController();

	GridBagLayout gbLayout = new GridBagLayout();
	setLayout(gbLayout);

	GridBagConstraints gbConstraints = new GridBagConstraints();
	gbConstraints.gridwidth = GridBagConstraints.REMAINDER;

        JPanel wordPanel = new JPanel();
	JLabel wordLabel = new JLabel(WORD_LABEL);
        wordPanel.add(wordLabel);

	this.wordSettingsDocumentListener = new WordSettingsDocumentListener(this.wordController);

	this.textField = new JTextField(INITIAL_WORD, 15);
        wordPanel.add(this.textField);

	gbLayout.setConstraints(wordPanel, gbConstraints);
        add(wordPanel);

        JPanel wordAudioPanel = new JPanel();
	wordAudioPanel.setBorder(BorderFactory.createTitledBorder(TITLE_WORD_AUDIO));

	WordSettingsActionListener wordSettingsActionListener = new WordSettingsActionListener(this.wordController);

	this.wordAudioPlayButton = new JButton(LABEL_WORD_PLAY);
	this.wordAudioPlayButton.setActionCommand(COMMAND_WORD_PLAY);
	this.wordAudioPlayButton.addActionListener(wordSettingsActionListener);
	wordAudioPanel.add(this.wordAudioPlayButton);

	this.wordAudioRecordButton = new JButton(LABEL_WORD_RECORD);
	this.wordAudioRecordButton.setActionCommand(COMMAND_WORD_RECORD);
	this.wordAudioRecordButton.addActionListener(wordSettingsActionListener);
	wordAudioPanel.add(this.wordAudioRecordButton);

	this.wordAudioStopButton = new JButton(LABEL_WORD_STOP);
	this.wordAudioStopButton.setActionCommand(COMMAND_WORD_STOP);
	this.wordAudioStopButton.addActionListener(wordSettingsActionListener);
	wordAudioPanel.add(this.wordAudioStopButton);

	gbLayout.setConstraints(wordAudioPanel, gbConstraints);
	add(wordAudioPanel);
    }

    public void updateModel()
    {
	String wordText = this.textField.getText();
	this.wordModel.setWord(wordText);
    }

    public void openView()
    {
    }

    public void closeView()
    {
    }

    public void opened()
    {
	this.textField.setText(this.wordModel.getWord());
	this.textField.setEnabled(true);
	this.textField.getDocument().addDocumentListener(this.wordSettingsDocumentListener);
	this.wordAudioPlayButton.setEnabled(true);
	this.wordAudioRecordButton.setEnabled(true);
	this.wordAudioStopButton.setEnabled(false);
    }

    public void closed()
    {
	this.textField.getDocument().removeDocumentListener(this.wordSettingsDocumentListener);
	this.textField.setEnabled(false);
	this.textField.setText(INITIAL_WORD);
	this.wordAudioPlayButton.setEnabled(false);
	this.wordAudioRecordButton.setEnabled(false);
	this.wordAudioStopButton.setEnabled(false);
    }

    public void modified()
    {
    }

    public void saved()
    {
    }

    public void recordStartedWord()
    {
	this.wordAudioPlayButton.setEnabled(false);
	this.wordAudioRecordButton.setEnabled(false);
	this.wordAudioStopButton.setEnabled(true);
    }

    public void recordStoppedWord()
    {
	this.wordAudioPlayButton.setEnabled(true);
	this.wordAudioRecordButton.setEnabled(true);
	this.wordAudioStopButton.setEnabled(false);
    }

    public void playStartedWordNotModified()
    {
	this.wordAudioPlayButton.setEnabled(false);
	this.wordAudioRecordButton.setEnabled(false);
	this.wordAudioStopButton.setEnabled(true);
    }

    public void playStoppedWordNotModified()
    {
	this.wordAudioPlayButton.setEnabled(true);
	this.wordAudioRecordButton.setEnabled(true);
	this.wordAudioStopButton.setEnabled(false);
    }

    public void playStartedWordModified()
    {
	this.wordAudioPlayButton.setEnabled(false);
	this.wordAudioRecordButton.setEnabled(false);
	this.wordAudioStopButton.setEnabled(true);
    }

    public void playStoppedWordModified()
    {
	this.wordAudioPlayButton.setEnabled(true);
	this.wordAudioRecordButton.setEnabled(true);
	this.wordAudioStopButton.setEnabled(false);
    }

    public void showTestingStatistics()
    {
    }

    public void showDirectedStatistics()
    {
    }
}
