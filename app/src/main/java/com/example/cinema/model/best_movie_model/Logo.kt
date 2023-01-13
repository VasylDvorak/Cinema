package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logo(
    val _id: String="",
    val url: String=""
) : Parcelable