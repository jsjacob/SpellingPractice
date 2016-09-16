/*
 * Window.java
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

import com.iamnota.SpellingPractice.Teacher.Controller;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Window
    extends JFrame
{
    private static final String TITLE = "Teacher - SpellingPractice";

    private Controller controller;

    public Window(Controller controller)
    {
	super(TITLE);
	//    frame.setResizable(false);

	this.controller = controller;

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		getController().quit();
	    }
	};
	addWindowListener(l);

	Container contentPane = getContentPane();
	WindowPanel windowPanel = new WindowPanel(this.controller);
	contentPane.add(windowPanel);

	pack();

	Dimension windowSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((screenSize.getWidth()/4.0) - (windowSize.getWidth()/2.0)), (int) ((screenSize.getHeight()/2.0) - (windowSize.getHeight()/2.0)));

	hide();
    }

    protected Controller getController()
    {
	return this.controller;
    }
}
