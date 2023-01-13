package com.example.cinema.model.model_stuio

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExternalId(
    val _id: String="",
    val imdb: String="",
    val kpHD: String="",
    val tmdb: Int=0
) : Parcelable