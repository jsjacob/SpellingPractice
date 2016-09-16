/*
 * Loaded.java
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

package com.iamnota.SpellingPractice.Teacher.state.word;

import com.iamnota.SpellingPractice.Teacher.WordController;
import com.iamnota.SpellingPractice.Teacher.WordModel;
import com.iamnota.SpellingPractice.Teacher.WordView;

public class Loaded
    extends State
{
    protected Loaded()
    {
	;
    }

    public String getName()
    {
	return "Loaded";
    }

    public State newWord(WordController wordController)
    {
	return this;
    }

    public State load(WordController wordController, String id)
    {
	return this;
    }

    public State unload(WordController wordController)
    {
	WordView wordView = wordController.getWordView();
	wordView.closed();

	WordModel wordModel = wordController.getWordModel();
	wordModel.clear();

	return Empty.getInstance();
    }

    public State save(WordController wordController)
    {
	return this;
    }

    public State delete(WordController wordController)
    {
	return this;
    }

    public State recordStartWord(WordController wordController)
    {
	return this;
    }

    public State playStartWord(WordController wordController)
    {
	return this;
    }
}
