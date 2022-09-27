package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.repositories

import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local.NoteDao
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.Note
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

class NoteRepositoryImpl (
    private val localDataSource: NoteDao
) : NoteRepository {

    override fun getAll(): Observable<List<Note>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Note(it.id,
                    it.title,
                    it.content,
                    it.archieved,
                    it.date)
                }
            }
    }

    override fun insert(noteEntity: Note): Completable {
        val noteEntity = NoteEntity(
            noteEntity.id,
            noteEntity.title,
            noteEntity.content,
            noteEntity.archieved,
            noteEntity.date
        )
        return localDataSource
            .insert(noteEntity)
    }

    override fun insertAll(noteEntities: List<Note>): Single<List<Long>> {
        TODO("Not yet implemented")
    }

    override fun updateContentById(id: Int, content: String): Completable {
        return Completable.fromCallable{
            val note = localDataSource.getById(id)
            val updateNote = note.copy(
                content = content
            )
            localDataSource.update(updateNote)
        }
    }

    override fun updateTitleById(id: Int, title: String): Completable {
        return Completable.fromCallable{
            val note = localDataSource.getById(id)
            val updateNote = note.copy(
                title = title
            )
            localDataSource.update(updateNote)
        }
    }

    override fun updateNoteById(id: Int, title: String, content: String, archieved: Boolean, date: String): Completable {
        return Completable.fromCallable{
            val note = localDataSource.getById(id)
            val updateNote = note.copy(
                title = title,
                content = content,
                archieved = archieved,
                date = date
            )
            localDataSource.update(updateNote)
        }
    }

    override fun deleteAll(): Completable {
        return localDataSource.deleteAll()
    }

    override fun deleteById(id: Int): Completable {
        return localDataSource.deleteById(id)
    }

    override fun getAllByTitleOrContent(filter: String): Observable<List<Note>> {
        Timber.e("Poziv noterepo")
        val ol = localDataSource.getByTitleOrContent(filter)
            .map {
                it.map{
                    Note(it.id,
                    it.title,
                    it.content,
                    it.archieved,
                    it.date
                    )
                }
            }
        return ol
    }

    override fun getArchievedNotes(): Observable<List<Note>> {
        return localDataSource
            .getArchievedNotes()
            .map {
                it.map {
                    Note(it.id,
                        it.title,
                        it.content,
                        it.archieved,
                        it.date)
                }
            }
    }

    override fun getNotesWhereDate(date: String): List<Note> {
        return localDataSource
            .getNotesWhereDate(date)
            .map {
                Note(
                    it.id,
                    it.title,
                    it.content,
                    it.archieved,
                    it.date
                )
            }
    }

    override fun getAll2(): Observable<List<Note>> {
        return localDataSource
            .getAll2("")
            .map {
                it.map {
                    Note(it.id,
                        it.title,
                        it.content,
                        it.archieved,
                        it.date)
                }
            }
    }
}