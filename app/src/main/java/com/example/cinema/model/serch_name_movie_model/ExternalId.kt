package com.example.cinema.model.serch_name_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExternalId(

    val _id: String="",
    val imdb: String="",
    val kpHD: String="",
    val tmdb: Int=0

) : Parcelable