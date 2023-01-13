package com.example.cinema.model.model_stuio

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    val _id: String="",
    var await: Int=0,
    var filmCritics: Double=0.0,
    val imdb: Double=0.0,
    var kp: Double=0.0,
    var russianFilmCritics: Int=0
) : Parcelable