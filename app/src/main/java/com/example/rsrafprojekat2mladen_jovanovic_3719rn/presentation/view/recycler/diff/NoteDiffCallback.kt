package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.title == newItem.title
                && oldItem.content == newItem.content
    }
}