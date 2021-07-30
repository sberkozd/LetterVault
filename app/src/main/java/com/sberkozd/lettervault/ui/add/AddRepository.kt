package com.sberkozd.lettervault.ui.add

import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.db.LetterDao
import javax.inject.Inject

class AddRepository @Inject constructor(private val letterDao: LetterDao) {

    suspend fun addNote(note: Note) {
        return letterDao.addNote(note)
    }
}