package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTO(
    var docs: MutableList<Docs> = mutableListOf(),
    val limit: Int=0,
    val page: Int=0,
    val pages: Int=0,
    val total: Int=0
) : Parcelable

fun nowPlaying(): MovieDTO = MovieDTO()

fun upcoming(): MovieDTO = MovieDTO()