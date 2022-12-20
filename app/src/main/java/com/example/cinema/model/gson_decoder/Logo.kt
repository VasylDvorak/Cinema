package com.example.cinema.model.gson_decoder
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Logo (

	@SerializedName("_id") val _id : String,
	@SerializedName("url") val url : String
): Parcelable