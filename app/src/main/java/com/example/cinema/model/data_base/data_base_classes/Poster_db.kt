package com.example.cinema.model.data_base.data_base_classes
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Poster_db (@PrimaryKey val id: UUID = UUID.randomUUID(),

					  val _id : String,
					  val url : String,
					  val previewUrl : String
): Parcelable