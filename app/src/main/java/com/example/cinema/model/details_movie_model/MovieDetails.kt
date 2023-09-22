package com.example.cinema.model.details_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetails(
    var ageRating: Int = 0,
    var countries: MutableList<Country> = mutableListOf(),
    var genres: MutableList<Genre> = mutableListOf(),
    var videos: Videos = Videos(),
) : Parcelable