package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTOBest(

    val docs: MutableList<DocsBest>? = mutableListOf(),
    val limit: Int?=0,
    val page: Int?=0,
    val pages: Int?=0,
    val total: Int?=0

) : Parcelable