package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatingBest(

    val _id: String="",
    val await: Double =0.0,
    val filmCritics: Double=0.0,
    val imdb: Double=0.0,
    val kp: Double=0.0,
    val russianFilmCritics: Int=0

) : Parcelable