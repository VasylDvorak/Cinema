package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Votes(
    val _id: String="",
    val await: Int=0,
    val filmCritics: Int=0,
    val imdb: Int=0,
    val kp: Int=0,
    val russianFilmCritics: Int=0
) : Parcelable