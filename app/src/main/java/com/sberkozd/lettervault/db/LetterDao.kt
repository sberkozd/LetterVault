package com.sberkozd.lettervault.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sberkozd.lettervault.data.Note

@Dao
interface LetterDao {

    @Update
    suspend fun updateUser(note: Note)

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun addNote(note: Note)




}

