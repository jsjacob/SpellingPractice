/*
 * LogViewImpl.java
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

import com.iamnota.SpellingPractice.Student.LogView;
import com.iamnota.SpellingPractice.Student.Controller;

import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.Dimension;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.FileWriter;


public class LogViewImpl
    implements LogView
{
    private static final String LEVEL_DEBUG = "debug";
    private static final String LEVEL_WARNING = "warning";
    private static final String LEVEL_ERROR = "error";

    private static final String HEADER_DEBUG = "[DEBUG] ";
    private static final String HEADER_WARNING = "[WARNING] ";
    private static final String HEADER_ERROR = "[ERROR] ";

    private static final String TITLE_WARNING = "Warning - Student - SpellingPractice";
    private static final String TITLE_ERROR = "Error - Student - SpellingPractice";

    private static final String LOG_FILENAME = "StudentLog.txt";

    private Controller controller;

    private FileWriter fileWriter;
    private PrintWriter printWriter;

    public LogViewImpl(Controller controller)
    {
	this.controller = controller;

	try
	{
	    this.fileWriter = new FileWriter(LOG_FILENAME);
	    this.printWriter = new PrintWriter(this.fileWriter);
	}

	catch (IOException e)
	{
	    this.fileWriter = null;
	    this.printWriter = null;

	    warning("Couldn't create logfile \"" + LOG_FILENAME + "\"");
	}
    }

    protected void _debug(String message)
    {
	System.out.println(HEADER_DEBUG + message);

	if (this.printWriter != null)
	{
	    this.printWriter.println(HEADER_DEBUG + message);
	    this.printWriter.flush();
	}
    }

    protected void _warning(String message)
    {
	if (this.printWriter != null)
	{
	    this.printWriter.println(HEADER_WARNING + message);
	    this.printWriter.flush();
	}

	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(message);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(400, 120));

	JOptionPane.showMessageDialog(null, textAreaScrollPane, TITLE_WARNING, JOptionPane.WARNING_MESSAGE);
    }

    protected void _error(String message)
    {
	if (this.printWriter != null)
	{
	    this.printWriter.println(HEADER_ERROR + message);
	    this.printWriter.flush();
	}

	// Use same font as JTextField.
	JTextField textfield = new JTextField();
	JTextArea textArea = new JTextArea(message);
	textArea.setFont(textfield.getFont());
	textArea.setWrapStyleWord(true);
	textArea.setLineWrap(true);
	textArea.setCaretPosition(0);
	textArea.setEditable(false);
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	textAreaScrollPane.setPreferredSize(new Dimension(400, 120));

	JOptionPane.showMessageDialog(null, textAreaScrollPane, TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
    }

    protected class DoDebug
	implements Runnable
    {
	private String message;

	public DoDebug(String message)
	{
	    this.message = message;
	}

	public void run()
	{
	    _debug(this.message);
	}
    }

    public void debug(String message)
    {
	if (isDebug())
	{
	    DoDebug doDebug = new DoDebug(message);
	    SwingUtilities.invokeLater(doDebug);
	}
    }

    protected class DoWarning
	implements Runnable
    {
	private String message;

	public DoWarning(String message)
	{
	    this.message = message;
	}

	public void run()
	{
	    _warning(this.message);
	}
    }

    public void warning(String message)
    {
	if (isWarning())
	{
	    DoWarning doWarning = new DoWarning(message);
	    SwingUtilities.invokeLater(doWarning);
	}
    }

    protected class DoError
	implements Runnable
    {
	private String message;

	public DoError(String message)
	{
	    this.message = message;
	}

	public void run()
	{
	    _error(this.message);
	}
    }

    public void error(String message)
    {
	if (isError())
	{
	    DoError doError = new DoError(message);
	    SwingUtilities.invokeLater(doError);
	}
    }

    protected class NullStream
	extends OutputStream
    {
	public void write(int b) {;}
    }

    public PrintStream getDebugStream()
    {
	if (isDebug())
	{
	    return System.out;
	}
	else
	{
	    return new PrintStream(new NullStream());
	}
    }

    protected boolean isDebug()
    {
	String logLevel = this.controller.getPreferenceModel().getLogLevel();

	if (logLevel == null)
	{
	    return false;
	}

	logLevel = logLevel.trim();

	if (logLevel.equalsIgnoreCase(LEVEL_DEBUG))
	{
	    return true;
	}

	return false;
    }


    protected boolean isWarning()
    {
	String logLevel = this.controller.getPreferenceModel().getLogLevel();

	if (logLevel == null)
	{
	    return true;
	}

	logLevel = logLevel.trim();

	if ((logLevel.equalsIgnoreCase(LEVEL_WARNING)) ||
	    (logLevel.equalsIgnoreCase(LEVEL_DEBUG)))
	{
	    return true;
	}

	return false;
    }

    protected boolean isError()
    {
	return true;
    }
}
