package com.sberkozd.lettervault.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timeUnlocked: String,
    val noteContext: String,
    val noteTitle: String,
    val isLocked: Int,
)