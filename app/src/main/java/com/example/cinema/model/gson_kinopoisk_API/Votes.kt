package com.example.cinema.model.gson_kinopoisk_API
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Votes (

	val kp : Int,
	val imdb : Int,
	val filmCritics : Int,
	val russianFilmCritics : Int,
	val await : Int,
	val _id : String
): Parcelable