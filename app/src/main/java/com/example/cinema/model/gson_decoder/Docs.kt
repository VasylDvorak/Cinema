package com.example.cinema.model.gson_decoder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(

    @SerializedName("externalId") val externalId: ExternalId? = null,
    @SerializedName("logo") val logo: Logo? = null,
    @SerializedName("poster") val poster: Poster? = null,
    @SerializedName("rating") val rating: Rating? = null,
    @SerializedName("votes") val votes: Votes? = null,
    @SerializedName("watchability") val watchability: Watchability? = null,
    @SerializedName("id") val id: Int = 0,
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