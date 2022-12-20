package com.example.cinema.model.gson_decoder
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Names (

	@SerializedName("_id") val _id : String,
	@SerializedName("name") val name : String
): Parcelable