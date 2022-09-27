package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.ListAdapter
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.LayoutItemNoteBinding
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.actions.OnClickListener
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.diff.NoteDiffCallback
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.viewholder.NoteViewHolder
import timber.log.Timber

class NotesAdapter(val archiveNote: (note: Note) -> Unit,
                   val deleteById: (note: Note) -> Unit,
                   val editActivity: (note: Note) -> Unit)
    : ListAdapter<Note, NoteViewHolder> (NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding = LayoutItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = NoteViewHolder(itemBinding,
            {archiveNote(getItem(it))},
            {deleteById(getItem(it))},
            {editActivity(getItem(it))})

        return vh
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}