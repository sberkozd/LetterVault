package com.sberkozd.lettervault.db

import androidx.room.Dao
import androidx.room.Update
import com.sberkozd.lettervault.data.Note

@Dao
interface LetterDao {

    @Update
    suspend fun updateUser(note: Note)
}

