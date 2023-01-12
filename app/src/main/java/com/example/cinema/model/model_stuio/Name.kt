package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Name(
    val _id: String="",
    val name: String=""
) : Parcelable