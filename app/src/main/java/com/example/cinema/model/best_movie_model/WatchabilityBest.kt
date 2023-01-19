package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WatchabilityBest(

    val _id: String?="",
    val items: String?=""

) : Parcelable