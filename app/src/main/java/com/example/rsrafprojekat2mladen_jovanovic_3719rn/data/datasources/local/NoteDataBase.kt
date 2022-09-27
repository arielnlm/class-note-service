package com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.data.models.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters()
abstract class NoteDataBase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}