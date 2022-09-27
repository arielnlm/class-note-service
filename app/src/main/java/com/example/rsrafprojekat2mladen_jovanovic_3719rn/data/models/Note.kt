package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models

import java.io.Serializable

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val archieved: Boolean,
    val date: String
) : Serializable

