package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local

import androidx.room.*
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.ClassEntity
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
abstract class ClassDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: ClassEntity): Completable

    @Query("SELECT * FROM classes")
    abstract fun getAll(): Observable<List<ClassEntity>>

    @Query("DELETE FROM classes")
    abstract fun deleteAll()

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<ClassEntity>): Completable


    @Query("SELECT * FROM classes WHERE predmet LIKE :name || '%' OR nastavnik LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<ClassEntity>>

    @Query("SELECT * FROM classes WHERE ((predmet LIKE '%' || :name || '%' OR nastavnik LIKE '%' || :name || '%') " +
            "AND dan LIKE '%' || :day || '%' AND grupe LIKE '%' || :className || '%')")
    abstract fun getByDayClassName(name: String, day:String, className: String): Observable<List<ClassEntity>>

    @Transaction
    open fun deleteAndInsertAll(entities: List<ClassEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}