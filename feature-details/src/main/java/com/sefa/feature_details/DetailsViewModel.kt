package com.sefa.feature_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sefa.domain.model.SingleMovie
import com.sefa.domain.usecase.DeleteMovieFromDbUseCase
import com.sefa.domain.usecase.GetIsMovieExistInDbUseCase
import com.sefa.domain.usecase.InsertMovieToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject
constructor(private val getIsMovieExistInDb: GetIsMovieExistInDbUseCase,
            private val insertMovieToDbUseCase: InsertMovieToDbUseCase,
            private val deleteMovieFromDbUseCase: DeleteMovieFromDbUseCase
) : ViewModel()
{
    private val isMovieExistInDb_ = MutableStateFlow<Boolean>(false)
    val getIsMovieExistInDb_: StateFlow<Boolean>
        get() = isMovieExistInDb_

    fun checkIfMovieExists(movieID:Int)
    {
        viewModelScope.launch {
            getIsMovieExistInDb.invoke(movieID=movieID)
                .distinctUntilChanged()
                .collect{ exist->
                    isMovieExistInDb_.value = exist
                }
        }
    }

    fun insertMovieDb(movie: SingleMovie)
    {
        viewModelScope.launch {
            insertMovieToDbUseCase.invoke(movie = movie)
        }
    }

    fun deleteFromDb(movieID: Int)
    {
        viewModelScope.launch {
            deleteMovieFromDbUseCase.invoke(movieID=movieID)
        }
    }
}