/*
 * ClassUtils.java
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

import java.lang.reflect.Constructor;


public class ClassUtils
{
    public static Object instantiateObject(String className, Object[] parameters)
	throws InstantiationException
    {
	Object object = null;

	try
	{
	    Class objectClass = Class.forName(className);
	    Constructor constructors[] = objectClass.getConstructors();
	    for (int i = 0; i < constructors.length; i++)
	    {
		Constructor constructor = constructors[i];
		Class constructorParameters []= constructor.getParameterTypes();
		if (constructorParameters.length == parameters.length)
		{
		    boolean isAssignable = true;
		    for (int j = 0; j < constructorParameters.length; j++)
		    {
			isAssignable = (isAssignable && (constructorParameters[j].isAssignableFrom(parameters[j].getClass())));
		    }

		    if (isAssignable)
		    {
			object = constructor.newInstance(parameters);
		    }
		}
	    }

	    if (object == null)
	    {
		throw new InstantiationException("Appropriate \"" + className + "\" constructor could not be found");
	    }
	}

	catch (ClassNotFoundException e)
	{
	    throw new InstantiationException("\"" + className + "\" can't be found");
	}

	catch (IllegalAccessException e)
	{
	    throw new InstantiationException("\"" + className + "\" constructor can't be accessed");
	}

	catch (InstantiationException e)
	{
	    throw new InstantiationException("\"" + className + "\" class is abstract");
	}

	catch (Exception e)
	{
	    throw new InstantiationException("\"" + className + "\" caused exception");
	}

	if (object == null)
	{
	    throw new InstantiationException("\"" + className + "\" should have been instantiated but wasn't");
	}

	return object;
    }
}
