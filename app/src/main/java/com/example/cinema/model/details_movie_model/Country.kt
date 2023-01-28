package com.example.cinema.model.details_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    var name: String =""
) : Parcelable