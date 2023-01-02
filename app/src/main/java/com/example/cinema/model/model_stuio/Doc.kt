package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doc(
    val alternativeName: String ="",
    val description: String="",
    val enName: String="",
    val externalId: ExternalId?=null,
    val id: Int=0,
    val logo: Logo?=null,
    val movieLength: Int=0,
    val name: String="",
    val names: List<Name>?=null,
    val poster: Poster?=null,
    val rating: Rating?=null,
    val releaseYears: List<Int> = listOf(),
    val shortDescription: String="",
    val type: String="",
    val votes: Votes?=null,
    val watchability: Watchability?=null,
    val year: Int=0,
    var isLike: Boolean = false,
    var url_trailer: String = ""
) : Parcelable