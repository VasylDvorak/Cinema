package com.example.cinema.model.data_base.data_base_classes
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Votes_db (

					 val kp : Int=0,
					 val imdb : Int=0,
					 val filmCritics : Int=0,
					 val russianFilmCritics : Int=0,
					 val await : Int=0,
					 val _id : String=""
): Parcelable