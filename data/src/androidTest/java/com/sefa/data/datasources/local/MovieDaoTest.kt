package com.sefa.data.datasources.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sefa.domain.model.SingleMovie
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest
{
    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDAO
    private lateinit var movie: SingleMovie

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = database.movieDao()
        movie = SingleMovie(1, "title1", "01.01.2001", 1.1, "poster1", "overview1")
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun testUpsertMovie() = runBlocking {

        // Given
        movieDao.upsertMovie(movie)
        val isContains = movieDao.isMovieExist(movie.id)

        // Then
        assertThat(isContains).isTrue()
    }

    @Test
    fun testDeleteMovie() = runBlocking {

        // When
        movieDao.upsertMovie(movie)
        movieDao.deleteMovie(movie.id)
        val isContains = movieDao.isMovieExist(movie.id)

        // Then
        assertThat(isContains).isFalse()
    }

    @Test
    fun testIsMovieExistExpectedReturnTrue() = runBlocking {

        // When
        movieDao.upsertMovie(movie)
        val isContains = movieDao.isMovieExist(movie.id)

        // Then
        assertThat(isContains).isTrue()
    }

    @Test
    fun testIsMovieExistExpectedReturnFalse() = runBlocking {

        // When
        movieDao.upsertMovie(movie)
        movieDao.deleteMovie(movie.id)
        val isContains = movieDao.isMovieExist(movie.id)

        // Then
        assertThat(isContains).isFalse()
    }

    @Test
    fun testGetAllMovies() = runBlocking {

    }

    /*@Test
    fun testgetAllMovies() = runBlocking {
        val movie1 = Movie(1,"title1","01.01.2001",1.1,"poster1","overview1")
        val movie2 = Movie(2,"title2","02.02.2002",2.2,"poster2","overview2")


        movieDao.upsertMovie(movie1)
        movieDao.upsertMovie(movie2)

        val flow = Pager(
            config = PagingConfig( pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = {movieDao.getAllMovies()}
        ).flow
            .collect{pagingData->
              //BURALARDA BİR YERDE BU FONKSİYON TAMAMLANMASI İÇİN EKLEMELER OLACAK BAKARSIN.
            }
    }*/

    /* val flow = Pager(
           config = PagingConfig( pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
           pagingSourceFactory = {movieDao.getAllMovies()}
       ).flow
           .collect{
             it.map {movie->
                 assertThat(movie).isEqualTo(movie1)
             }
           }*/
}
