package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.contract

import androidx.lifecycle.LiveData
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.NoteEntity
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states.ClassesState
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.states.NotesState

interface MainContract {

    interface ViewModel{
        val classState: LiveData<ClassesState>
        val notesState: LiveData<NotesState>
        // val addDone: LiveData<AddClassState>

        // fun addClass(myClass: Class)
        fun fetchAllClasses()
        fun getAllClasses()
        fun getClassesByName(name: String)
        fun getClassesByNameDayClass(name: String, day:String, className: String)

        fun getAllNotes()
        fun getAllNotes2()
        fun getNotesByTitleOrContent(filter: String)
        fun deleteAllNotes()
        fun deleteNote(id: Int)
        fun insertNote(entity: Note)
        fun insertAllNotes(entities: List<Note>)
        fun updateNote(id: Int, title: String, content: String, archived: Boolean, date: String)
        fun getFiveLastDaysNote(startStats: (input: IntArray) -> Unit)
        fun getArchievedNotes()
    }
}