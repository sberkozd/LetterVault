package com.sberkozd.lettervault.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sberkozd.lettervault.data.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)

abstract class LetterDatabase : RoomDatabase() {

    abstract fun letterDao(): LetterDao

}