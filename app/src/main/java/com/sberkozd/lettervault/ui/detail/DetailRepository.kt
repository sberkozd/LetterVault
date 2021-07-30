package com.sberkozd.lettervault.ui.detail

import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.db.LetterDao
import javax.inject.Inject

class DetailRepository @Inject constructor(private val letterDao: LetterDao) {

    suspend fun getAllNotes(): List<Note> {
        return letterDao.getAllNotes()
    }

}