/*
 * version.java
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

package com.iamnota.SpellingPractice.Student;

public class version
{
    private static final String NAME = "SpellingPractice - Student";
    private static final String VERSION = "Version 0.5.0";
    private static final String COPYRIGHT = "Copyright (C) 2002-2004 John S. Jacob";
    private static final String DATE = "Released January 4, 2004";
    private static final String URL = "http://www.spellingpractice.org/";

    public static String getInfoText()
    {
	String lineSeparator = System.getProperty("line.separator");
	return NAME + lineSeparator + VERSION + lineSeparator + COPYRIGHT + lineSeparator + DATE + lineSeparator + URL;
    }

    public static String getInfoHtml()
    {
	return NAME + "<br>" + VERSION + "<br>" + COPYRIGHT + "<br>" + DATE + "<br>" + URL;
    }

    private static final String LICENSE_1 = "SpellingPractice is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.";
    private static final String LICENSE_2 = "SpellingPractice is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.";
    private static final String LICENSE_3 = "You should have received a copy of the GNU General Public License along with SpellingPractice; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.";

    public static String getLicenseText()
    {
	String lineSeparator = System.getProperty("line.separator");
	return LICENSE_1 + lineSeparator + lineSeparator + LICENSE_2 + lineSeparator + lineSeparator + LICENSE_3;
    }

    public static String getLicenseHtml()
    {
	return "<p>" + LICENSE_1 + "</p><p>" + LICENSE_2 + "</p><p>" + LICENSE_3 + "</p>";
    }
}
