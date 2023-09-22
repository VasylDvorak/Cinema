package com.example.cinema.model.serch_name_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(

    val _id: String="",
    var await: Int=0,
    var filmCritics: Double?=0.0,
    val imdb: Double=0.0,
    var kp: Double=0.0,
    var russianFilmCritics: Int=0

) : Parcelable


