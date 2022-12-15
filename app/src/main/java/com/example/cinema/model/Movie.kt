package com.example.cinema.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val movie_title: String,
    val original_title: String,
    val picture: Int
) : Parcelable
