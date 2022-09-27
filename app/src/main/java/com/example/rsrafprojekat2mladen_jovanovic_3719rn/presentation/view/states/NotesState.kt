package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note

sealed class NotesState {
    object Loading: NotesState()
    data class Success(val notes: List<Note>): NotesState()
    data class Error(val message: String): NotesState()
}