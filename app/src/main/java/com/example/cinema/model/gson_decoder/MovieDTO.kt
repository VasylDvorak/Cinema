package com.example.cinema.model.gson_decoder

import android.graphics.Insets.add
import android.os.Parcelable
import com.example.cinema.model.AboutMovie
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieDTO (

	@SerializedName("docs")  val docs: List<Docs> = listOf(),
	@SerializedName("total") val total : Int =1,
	@SerializedName("limit") val limit : Int = 1,
	@SerializedName("page") val page : Int = 1,
	@SerializedName("pages") val pages : Int = 1,

): Parcelable


fun nowPlaying(): List<MovieDTO> = mutableListOf(MovieDTO())

fun upcoming(): List<MovieDTO> = mutableListOf(MovieDTO())
