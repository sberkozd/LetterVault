package com.sberkozd.lettervault.db

import androidx.room.*
import com.sberkozd.lettervault.data.Note

@Dao
interface LetterDao {

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteByID(id: Int): Note?

    @Delete
    suspend fun deleteNote(note: Note)




}

