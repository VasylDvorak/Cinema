package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ExternalId(

    val kpHD: String="",
    val imdb: String="",
    val _id: String=""
) : Parcelable