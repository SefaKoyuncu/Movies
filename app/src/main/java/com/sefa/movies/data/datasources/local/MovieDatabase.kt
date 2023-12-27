package com.sefa.movies.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sefa.movies.domain.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase()
{
    abstract fun movieDao() : MovieDAO
}