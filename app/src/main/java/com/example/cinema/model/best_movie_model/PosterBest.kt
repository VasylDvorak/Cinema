package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PosterBest(

    val _id: String?="",
    val previewUrl: String?="",
    val url: String?=""

) : Parcelable