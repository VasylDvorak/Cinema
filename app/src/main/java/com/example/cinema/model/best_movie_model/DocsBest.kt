package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DocsBest(

    val alternativeName: String?="",
    val description: String?="",
    val enName: String?="",
    val externalId: ExternalIdBest? = ExternalIdBest(),
    val id: Int?=0,
    val logo: LogoBest?= LogoBest(),
    val movieLength: Int?=0,
    val name: String?="",
    val names: MutableList<NameBest>? =  mutableListOf(),
    val poster: PosterBest? = PosterBest(),
    val rating: RatingBest? = RatingBest(),
    val releaseYears: MutableList<String>? = mutableListOf(),
    val shortDescription: String? = "",
    val type: String? = "",
    val votes: VotesBest? = VotesBest(),
    val watchability: WatchabilityBest? = WatchabilityBest(),
    val year: Int?=0

) : Parcelable