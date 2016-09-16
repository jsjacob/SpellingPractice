/*
 * TextUtils.java
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

package com.iamnota.SpellingPractice.common;

import java.text.BreakIterator;
import java.io.PrintStream;


public class TextUtils
{
    public static void printLines(PrintStream out, int columns, String text)
    {
	String lineSeparator = System.getProperty("line.separator");

	BreakIterator breakIterator = BreakIterator.getWordInstance();
	breakIterator.setText(text);
	int column = 0;
	int start = breakIterator.first();
	for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next())
	{
	    String subString = text.substring(start, end);

	    if ((column > 0) && ((column + (end-start)) > (columns-1)))
	    {
		while ((!subString.equals(lineSeparator)) && (Character.isWhitespace(subString.charAt(0))))
		{
		    start = end;
		    end = breakIterator.next();
		    subString = text.substring(start, end);
		}

		if (!subString.equals(lineSeparator))
		{
		    out.println();
		    column = 0;
		}
	    }

	    while ((column == 0) && ((end-start) > (columns-1)))
	    {
		out.println(text.substring(start, start+columns-1));
		start += (columns-1);
		subString = text.substring(start, end);
	    }

	    out.print(subString);

	    if (!subString.equals(lineSeparator))
	    {
		column += (end-start);
	    }
	    else
	    {
		column = 0;
	    }
	}
    }

    public static String replaceTokens(String template, String[] replacements)
    {
	String workingTemplate = new String(template);

	for (int replacementIndex = 0; replacementIndex < replacements.length; replacementIndex++)
	{
	    String token = "\\" + replacementIndex;

	    int index = -1;
	    while ((index = workingTemplate.indexOf(token)) != -1)
	    {
		StringBuffer workingTemplateBuffer = new StringBuffer(workingTemplate);
		workingTemplateBuffer.replace(index, (index+(token.length())), replacements[replacementIndex]);
		workingTemplate = workingTemplateBuffer.toString();
	    }
	}

	return workingTemplate;
    }
}
