package com.sefa.data.datasources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sefa.domain.model.SingleMovie

@Dao
interface MovieDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(movie: SingleMovie) //update and insert

    @Query("DELETE FROM movies WHERE id = :movieID")
    suspend fun deleteMovie(movieID: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :movieID) AS RESULT")
    suspend fun isMovieExist(movieID: Int) : Boolean

    @Query("SELECT * FROM movies")
    fun getFavMovies() : PagingSource<Int, SingleMovie>
}