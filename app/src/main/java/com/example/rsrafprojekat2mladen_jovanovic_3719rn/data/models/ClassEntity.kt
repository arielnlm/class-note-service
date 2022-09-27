package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "predmet")
    val subject: String,
    @ColumnInfo(name = "tip")
    val type: String,
    @ColumnInfo(name = "nastavnik")
    val teacher: String,
    @ColumnInfo(name = "grupe")
    val groups: String,
    @ColumnInfo(name = "dan")
    val day: String,
    @ColumnInfo(name = "termin")
    val time: String,
    @ColumnInfo(name = "ucionica")
    val classroom: String
)