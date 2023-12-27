package com.sefa.movies.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sefa.movies.domain.model.Movie

@Dao
interface MovieDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(movie: Movie) //update and insert

    @Query("SELECT COUNT(*) FROM movies WHERE id = :movieID")
    suspend fun getMovieCount(movieID: Int): Int

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :movieID)")
    suspend fun isMovieExist(movieID: Int): Boolean {    // movieID varsa true yoksa false döncek
        return getMovieCount(movieID) > 0
    }

    @Query("DELETE FROM movies WHERE id = :movieID")
    suspend fun deleteMovie(movieID: Int)
}