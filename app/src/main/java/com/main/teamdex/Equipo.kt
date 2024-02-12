package com.main.teamdex

import android.os.Parcel
import android.os.Parcelable

data class Equipo(
    val listaId: IntArray?,
    val listaPokemon: MutableList<Pokemon>,
    var fav: Boolean = false,
    val autor: String?,
    val nombre: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createIntArray(),
        TODO("listaPokemon"),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(listaId)
        parcel.writeByte(if (fav) 1 else 0)
        parcel.writeString(autor)
        parcel.writeString(nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Equipo> {
        override fun createFromParcel(parcel: Parcel): Equipo {
            return Equipo(parcel)
        }

        override fun newArray(size: Int): Array<Equipo?> {
            return arrayOfNulls(size)
        }
    }
}