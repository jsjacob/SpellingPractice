/*
 * SpellingWindow.java
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
import com.iamnota.SpellingPractice.Student.SpellingView;
import com.iamnota.SpellingPractice.Student.SpellingModel;
import com.iamnota.SpellingPractice.Student.StudentModel;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SpellingWindow
    extends JFrame
{
    private static final String TITLE = "Student - SpellingPractice";
    private static final String TITLE_POSTFIX = " - SpellingPractice";

    private Controller controller;
    private SpellingModel spellingModel;

    private SpellingPanel spellingPanel;

    public SpellingWindow(Controller controller, SpellingModel spellingModel)
    {
	super(TITLE);
	//    frame.setResizable(false);

	this.controller = controller;
	this.spellingModel = spellingModel;

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		getController().logout();
	    }
	};
	addWindowListener(l);

	Container contentPane = getContentPane();
	this.spellingPanel = new SpellingPanel(this.controller, this.spellingModel);
	contentPane.add(spellingPanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/2.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	setVisible(false);
    }

    protected Controller getController()
    {
	return this.controller;
    }

    public void clear()
    {
	StudentModel studentModel = this.controller.getStudentModel();
	setTitle(studentModel.getName() + TITLE_POSTFIX);

	this.spellingPanel.clear();
	repaint();
    }

    public void loadStarted()
    {
	this.spellingPanel.loadStarted();
	repaint();
    }

    public void loadStopped()
    {
	this.spellingPanel.loadStopped();
	repaint();
    }

    public void listenStarted()
    {
	this.spellingPanel.listenStarted();
	repaint();
    }

    public void listenStopped()
    {
	this.spellingPanel.listenStopped();
	repaint();
    }

    public void showResultStarted()
    {
	this.spellingPanel.showResultStarted();
	repaint();
    }

    public void showResultStopped()
    {
	this.spellingPanel.showResultStopped();
	repaint();
    }

    public void showNoWordsStarted()
    {
	this.spellingPanel.showNoWordsStarted();
	repaint();
    }

    public void showNoWordsStopped()
    {
	this.spellingPanel.showNoWordsStopped();
	repaint();
    }
}
