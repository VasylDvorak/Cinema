package com.example.cinema.model.model_stuio

import android.os.Parcelable
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.model_stuio.MovieDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTO(
    val docs: List<Doc> = listOf(),
    val limit: Int =0,
    val page: Int=0,
    val pages: Int=0,
    val total: Int=0
) : Parcelable

fun nowPlaying(): MovieDTO = MovieDTO()

fun upcoming(): MovieDTO = MovieDTO()
