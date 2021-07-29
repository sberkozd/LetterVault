package com.sberkozd.lettervault.ui.grid

import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.db.LetterDao
import javax.inject.Inject

class GridRepository @Inject constructor(private val letterDao: LetterDao) {

    suspend fun getAllNotes(): List<Note> {
        return letterDao.getAllNotes()
    }

}