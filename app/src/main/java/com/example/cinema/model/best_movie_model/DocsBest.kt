package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DocsBest(

    val alternativeName: String="",
    val description: String="",
    val enName: String="",
    val externalIdBest: ExternalIdBest = ExternalIdBest(),
    val id: Int=0,
    val logoBest: LogoBest= LogoBest(),
    val movieLength: Int=0,
    val name: String="",
    val nameBests: MutableList<NameBest> =  mutableListOf(),
    val posterBest: PosterBest = PosterBest(),
    val ratingBest: RatingBest = RatingBest(),
    val releaseYears: MutableList<String> = mutableListOf(),
    val shortDescription: String = "",
    val type: String = "",
    val votesBest: VotesBest = VotesBest(),
    val watchabilityBest: WatchabilityBest = WatchabilityBest(),
    val year: Int=0

) : Parcelable