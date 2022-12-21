package com.example.cinema.model.gson_decoder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(

	@SerializedName("externalId") val externalId: ExternalId?,
	@SerializedName("logo") val logo: Logo?,
	@SerializedName("poster") val poster: Poster,
	@SerializedName("rating") val rating: Rating,
	@SerializedName("votes") val votes: Votes?,
	@SerializedName("watchability") val watchability: Watchability?,
	@SerializedName("id") val id: Int,
	@SerializedName("alternativeName") val alternativeName: String?,
	@SerializedName("description") val description: String,
	@SerializedName("enName") val enName: String?,
	@SerializedName("movieLength") val movieLength: Int,
	@SerializedName("name") val name: String,
	@SerializedName("names") val names: List<Names>?,
	@SerializedName("shortDescription") val shortDescription: String?,
	@SerializedName("type") val type: String,
	@SerializedName("year") val year: Int,
	@SerializedName("releaseYears") val releaseYears: List<String>?,
	var isLike: Boolean = false
): Parcelable