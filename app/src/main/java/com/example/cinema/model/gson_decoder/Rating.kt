package com.example.cinema.model.gson_decoder
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating (

	@SerializedName("_id") val _id : String,
	@SerializedName("kp") val kp : Int,
	@SerializedName("imdb") val imdb : Int,
	@SerializedName("filmCritics") val filmCritics : Int,
	@SerializedName("russianFilmCritics") val russianFilmCritics : Int,
	@SerializedName("await") val await : Int
): Parcelable