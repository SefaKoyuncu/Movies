package com.sefa.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sefa.domain.model.SingleMovie

@Database(entities = [SingleMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase()
{
    abstract fun movieDao() : MovieDAO
}