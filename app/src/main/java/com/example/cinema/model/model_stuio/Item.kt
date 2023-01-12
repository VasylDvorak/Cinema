package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Item(
    val _id: String = "",
    val logo: Logo=Logo(),
    val name: String= "",
    val url: String= ""
) : Parcelable