package com.example.cinema.model.model_stuio

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logo(
    val _id: String="",
    val url: String=""
) : Parcelable