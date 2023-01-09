package com.example.cinema.model.gson_kinopoisk_API

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Rating(
    val _id: String="",
    val kp: Int=0,
    val imdb: Int=0,
    val filmCritics: Int=0,
    val russianFilmCritics: Int=0,
    val await: Int=0
) : Parcelable