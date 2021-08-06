package com.sberkozd.lettervault.ui.detail

import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.db.LetterDao
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DetailRepository @Inject constructor(private val letterDao: LetterDao) {

    suspend fun getAllNotes(): List<Note> {
        return letterDao.getAllNotes()
    }

    suspend fun deleteNote(note: Note) {
        letterDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note) {
        letterDao.updateNote(note)
    }

    suspend fun getNoteByID(id : Int): Note?{
        return letterDao.getNoteByID(id)
    }


}