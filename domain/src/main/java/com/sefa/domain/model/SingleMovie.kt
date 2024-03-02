package com.sefa.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sefa.domain.util.Constant.BASE_IMAGE_URL
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class SingleMovie(
    @PrimaryKey //autoGenerate = true
    var id: Int = 0,
    var title: String = "",
    var release_date: String = "",
    var vote_average: Double = 0.0,
    var poster_path: String = "",
    var poster_url: String = (BASE_IMAGE_URL + poster_path),
    var overview: String = ""
) : Parcelable