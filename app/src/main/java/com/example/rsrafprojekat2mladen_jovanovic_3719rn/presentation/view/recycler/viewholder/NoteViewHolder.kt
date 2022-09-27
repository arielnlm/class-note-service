package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.databinding.LayoutItemNoteBinding
import timber.log.Timber

class NoteViewHolder(
    private val itemBinding: LayoutItemNoteBinding,
    val archiveNote: (pos: Int) -> Unit,
    val deleteById: (pos: Int) -> Unit,
    val editActivity: (pos: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

    init{

        itemBinding.noteDeleteButton.setOnClickListener{
            deleteById(layoutPosition)
        }

        itemBinding.noteArchiveBttn.setOnClickListener{
            archiveNote(layoutPosition)
        }



        itemBinding.noteEditButton.setOnClickListener{
            Timber.e("Klikno na edit")
            editActivity(layoutPosition)
        }

    }

    fun bind(note: Note){
        Timber.e("Bind called")
        if(note.archieved)
            itemBinding.noteTitleTv.text = note.title + "[A]"
        else
            itemBinding.noteTitleTv.text = note.title

        itemBinding.noteContentTv.text = note.content
        if(note.archieved){
            itemBinding.noteArchiveBttn.setBackgroundResource(R.drawable.ic_baseline_unarchive_24)
        }
        else{
            itemBinding.noteArchiveBttn.setBackgroundResource(R.drawable.ic_baseline_archive_24)
        }
    }
}