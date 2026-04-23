package com.example.personallifelogger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a single life log entry.
 */
@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val imageUri: String? = null,
    val audioPath: String? = null,  // New field for audio recording
    val date: Long = System.currentTimeMillis()
)