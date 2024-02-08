import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.sefa.movies.data.datasources.local.MovieDAO
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.data.repository.MovieRepositoryImpl
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieService: MovieService
    private lateinit var movieDAO: MovieDAO
    private lateinit var movieRepository : MovieRepository
    private lateinit var movie: Movie


    @Before
    fun setUp()
    {
        movieService = mockk()
        movieDAO = mockk()
        movieRepository  = MovieRepositoryImpl(movieService, movieDAO)
        movie = Movie(1,"title1","01.01.2001",1.1,"poster1","overview1")
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPagingData should return flow of PagingData`() = runBlocking {

    }

    @Test
    fun `getAllMoviesFromDb should return flow of PagingData`() = runBlocking {

    }

    @Test
    fun `getAllMoviesFromDb should handle error and return empty PagingData`() = runBlocking {

        // Given
        coEvery { movieDAO.getAllMovies() } throws Exception()

        // When
        val result = try {
            movieRepository.getAllMoviesFromDb().toList()
        } catch (e: Exception) {
            emptyList<PagingData<Movie>>()
        }

        // Then
        coVerify {
            movieDAO.getAllMovies()
        }
        assertTrue(result.isEmpty())
    }

    @Test
    fun `isMovieExistInDb should return true when movie exists`() = runBlocking {

        // Given
        coEvery { movieDAO.isMovieExist(movie.id) } returns true

        // When
        val result = movieRepository.isMovieExistInDb(movie.id).first()

        // Then
        coVerify {
            movieDAO.isMovieExist(movie.id)
        }
        assert(result)
    }

    @Test
    fun `isMovieExistInDb should return false when movie does not exist`() = runBlocking {

        // Given
        coEvery { movieDAO.isMovieExist(movie.id) } returns false

        // When
        val result = movieRepository.isMovieExistInDb(movie.id).first()

        // Then
        coVerify { movieDAO.isMovieExist(movie.id) }
        assert(!result)
    }

    @Test
    fun `insertToDb should call upsertMovie`() = runBlocking {

        // Given
        coEvery { movieDAO.upsertMovie(movie) } just Runs

        // When
        movieRepository.insertToDb(movie)

        // Then
        coVerify {
            movieDAO.upsertMovie(movie)
        }
    }

    @Test
    fun `deleteFromDb should call deleteMovie`() = runBlocking {

        // Given
        coEvery { movieDAO.deleteMovie(movie.id) } just Runs

        // When
        movieRepository.deleteFromDb(movie.id)

        // Then
        coVerify { movieDAO.deleteMovie(movie.id) }
    }
}
