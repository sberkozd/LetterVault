package com.sberkozd.lettervault.ui.detail

import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.db.LetterDao
import javax.inject.Inject

class DetailRepository @Inject constructor(private val letterDao: LetterDao) {

    suspend fun deleteNote(note: Note) {
        letterDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note) {
        letterDao.updateNote(note)
    }

    suspend fun getNoteByID(id: Int): Note? {
        return letterDao.getNoteByID(id)
    }


}