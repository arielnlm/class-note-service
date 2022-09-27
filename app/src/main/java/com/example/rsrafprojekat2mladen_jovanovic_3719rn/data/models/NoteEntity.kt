package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val archieved: Boolean,
    val date: String

)
