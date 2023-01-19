package com.example.cinema.model.serch_name_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Watchability(

    val _id: String="",
    val items: MutableList<Item> = mutableListOf()

) : Parcelable