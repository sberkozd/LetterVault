package com.sberkozd.lettervault.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var timeUnlocked: String,
    var noteContext: String,
    var noteTitle: String,
    var isLocked: Int,
)