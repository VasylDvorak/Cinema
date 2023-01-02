package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Watchability(
    val _id: String,
    val items: String
) : Parcelable