package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.actions

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note

class OnClickListener(val clickListener: (note: Note) -> Unit) {
    fun onClick(note: Note) = clickListener(note)
}