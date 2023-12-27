package com.sefa.movies.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class Movie(
    @PrimaryKey //autoGenerate = true
    var id: Int = 0,
    var title: String = "",
    var release_date: String="",
    var vote_average: Double=0.0,
    var poster_path: String="",
    var overview: String="",
    var backdrop_path: String =""
) : Parcelable