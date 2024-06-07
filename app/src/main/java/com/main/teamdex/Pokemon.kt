package com.main.teamdex

import android.os.Parcel
import android.os.Parcelable

data class Pokemon (
    val id : Int,
    val num : Int,
    val habilidad : String,
    val item : String,
    val shiny : Int
)