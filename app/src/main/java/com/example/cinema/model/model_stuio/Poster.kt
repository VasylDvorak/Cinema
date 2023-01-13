package com.example.cinema.model.model_stuio

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poster(
    val _id: String="",
    val previewUrl: String="",
    var url: String=""
) : Parcelable