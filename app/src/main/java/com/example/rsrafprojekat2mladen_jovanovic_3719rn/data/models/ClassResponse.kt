package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClassResponse(
    val id: Int? = null,
    @Json(name = "predmet")
    val subject: String,
    @Json(name = "tip")
    val type: String,
    @Json(name = "nastavnik")
    val teacher: String,
    @Json(name = "grupe")
    val groups: String,
    @Json(name = "dan")
    val day: String,
    @Json(name = "termin")
    val time: String,
    @Json(name = "ucionica")
    val classroom: String
)