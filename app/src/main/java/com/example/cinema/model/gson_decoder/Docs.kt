package com.example.cinema.model.gson_decoder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(

    val externalId: ExternalId? = null,
    val logo: Logo? = null,
    val poster: Poster? = null,
    val rating: Rating? = null,
    val votes: Votes? = null,
    val watchability: Watchability? = null,
    val id: Int = 0,
    @SerializedName("alternativeName") val alternativeName: String? = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("enName") val enName: String? = "",
    @SerializedName("movieLength") val movieLength: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("names") val names: List<Names>? = null,
    @SerializedName("shortDescription") val shortDescription: String? = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("year") val year: Int = 0,
    @SerializedName("releaseYears") val releaseYears: List<String>? = null,
    @SerializedName("isLike") var isLike: Boolean = false,
    @SerializedName("url") var url_trailer: String = ""
) : Parcelable