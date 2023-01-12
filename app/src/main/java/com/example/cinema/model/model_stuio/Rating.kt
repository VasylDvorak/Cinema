package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    val _id: String="",
    val await: Int=0,
    val filmCritics: Double=0.0,
    val imdb: Double=0.0,
    val kp: Double=0.0,
    val russianFilmCritics: Int=0
) : Parcelable