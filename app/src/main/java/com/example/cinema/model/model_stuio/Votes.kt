package com.example.cinema.model.model_stuio
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Votes (

				  val kp : Int=0,
				  val imdb : Int=0,
				  val filmCritics : Int=0,
				  val russianFilmCritics : Int=0,
				  val await : Int=0,
				  val _id : String=""
): Parcelable