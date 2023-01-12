package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReleaseYear(
    val _id: String="",
    val end: Int=0,
    val start: Int=0
) : Parcelable