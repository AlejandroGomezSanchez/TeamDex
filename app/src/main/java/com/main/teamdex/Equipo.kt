package com.main.teamdex

data class Equipo(
    val listaId: IntArray,
    val listaPokemon: MutableList<Pokemon>,
    val fav: Boolean = false,
    val autor: String,
    val nombre: String
)