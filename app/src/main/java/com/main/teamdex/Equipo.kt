package com.main.teamdex

import android.os.Parcel
import android.os.Parcelable

data class Equipo(
    val id : Int,
    val listaId: IntArray?,
    val listaPokemon: MutableList<Pokemon>,

    val autor: String?,
    val nombre: String?,
    var anotacion:String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createIntArray(),
        TODO("listaPokemon"),

        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeIntArray(listaId)

        parcel.writeString(autor)
        parcel.writeString(nombre)
        parcel.writeString(anotacion)
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