package com.example.cinema.model.serch_name_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poster(

    val _id: String="",
    val previewUrl: String="",
    var url: String=""

) : Parcelable

