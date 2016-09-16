/*
 * IdleModified.java
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
import com.iamnota.SpellingPractice.Teacher.WordAudio;
import com.iamnota.SpellingPractice.Teacher.WordView;

public class IdleModified
    extends Idle
{
    private static IdleModified me;

    protected IdleModified()
    {
	;
    }

    public static synchronized IdleModified getInstance()
    {
	if (IdleModified.me == null)
	{
	    IdleModified.me = new IdleModified();
	}

	return IdleModified.me;
    }

    public String getName()
    {
	return "IdleModified";
    }

    public State closeView(WordController wordController)
    {
	// ask to save changes
	WordView wordView = wordController.getWordView();

	if (wordView.isAskSaveChanges())
	{
	    wordView.updateModel();

	    return SavingCloseView.getInstance();
	}
	else
	{
	    return super.unload(wordController).closeView(wordController);
	}
    }

    public State newWord(WordController wordController)
    {
	// ask to save changes
	WordView wordView = wordController.getWordView();

	if (wordView.isAskSaveChanges())
	{
	    wordView.updateModel();

	    return SavingNewWord.getInstance();
	}
	else
	{
	    return super.unload(wordController).newWord(wordController);
	}
    }

    public State load(WordController wordController, String id)
    {
	// ask to save changes
	WordView wordView = wordController.getWordView();

	if (wordView.isAskSaveChanges())
	{
	    wordView.updateModel();

	    return SavingLoad.getInstance(id);
	}
	else
	{
	    return super.unload(wordController).load(wordController, id);
	}
    }

    public State unload(WordController wordController)
    {
	// ask to save changes
	WordView wordView = wordController.getWordView();

	if (wordView.isAskSaveChanges())
	{
	    wordView.updateModel();

	    return SavingUnload.getInstance();
	}
	else
	{
	    return super.unload(wordController);
	}
    }

    public State playStartWord(WordController wordController)
    {
	WordAudio wordAudio = wordController.getWordAudio();
	wordAudio.playStartWord();
	return PlayingWordModified.getInstance();
    }
}
