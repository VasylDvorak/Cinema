package com.example.cinema.model.gson_kinopoisk_API
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Names (

	val _id : String,
	val name : String
): Parcelable