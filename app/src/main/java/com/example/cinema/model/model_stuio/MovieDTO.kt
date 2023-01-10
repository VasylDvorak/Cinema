package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieDTO(

    val docs: MutableList<Docs> = mutableListOf(),
    val total: Int = 1,
    val limit: Int = 1,
    val page: Int = 1,
    val pages: Int = 1,

    ) : Parcelable


fun nowPlaying(): MovieDTO = MovieDTO()

fun upcoming(): MovieDTO = MovieDTO()
