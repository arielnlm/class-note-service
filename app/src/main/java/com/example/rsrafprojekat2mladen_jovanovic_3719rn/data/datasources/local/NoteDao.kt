package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local

import androidx.room.*
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.NoteEntity
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.internal.wait

@Dao
abstract class NoteDao {

    @Query("SELECT * FROM notes")
    abstract fun getAll(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :empty || '%'")
    abstract fun getAll2(empty: String): Observable<List<NoteEntity>>

    @Query("DELETE FROM notes")
    abstract fun deleteAll(): Completable

    @Query("DELETE FROM notes WHERE id == :id")
    abstract fun deleteById(id: Int): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<NoteEntity>): Completable

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: NoteEntity): Completable

    @Query("SELECT * FROM notes WHERE (title LIKE '%' || :filter || '%' OR content LIKE '%' || :filter || '%')")
    abstract fun getByTitleOrContent(filter: String): Observable<List<NoteEntity>>

    @Transaction
    open fun deleteAndInsertAll(entities: List<NoteEntity>){
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Update
    abstract fun update(entity: NoteEntity)

    @Query("SELECT * FROM notes WHERE id == :id")
    abstract fun getById(id: Int): NoteEntity

    @Query("SELECT * FROM notes WHERE archieved == 1")
    abstract fun getArchievedNotes(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE date == :date")
    abstract fun getNotesWhereDate(date: String): List<NoteEntity>

}