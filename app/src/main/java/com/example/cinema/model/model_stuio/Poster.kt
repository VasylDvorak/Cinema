package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poster(
    val _id: String="",
    val url: String="",
    val previewUrl: String=""
) : Parcelable