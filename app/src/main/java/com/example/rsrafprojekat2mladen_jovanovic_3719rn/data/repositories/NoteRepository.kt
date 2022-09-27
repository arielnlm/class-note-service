package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface NoteRepository {

    fun getAll(): Observable<List<Note>>

    fun insert(noteEntity: Note): Completable

    fun insertAll(noteEntities: List<Note>): Single<List<Long>>

    fun updateContentById(id: Int, content: String): Completable

    fun updateTitleById(id: Int, title: String): Completable

    fun updateNoteById(id: Int, title: String, content: String, archieved: Boolean, date: String): Completable

    fun deleteAll(): Completable

    fun deleteById(id: Int): Completable

    fun getAllByTitleOrContent(filter: String): Observable<List<Note>>

    fun getArchievedNotes(): Observable<List<Note>>

    fun getNotesWhereDate(date: String): List<Note>

    fun getAll2(): Observable<List<Note>>
}