package com.example.cinema.model.gson_decoder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExternalId (

	@SerializedName("kpHD") val kpHD : String,
	@SerializedName("imdb") val imdb : String,
	@SerializedName("_id") val _id : String
): Parcelable