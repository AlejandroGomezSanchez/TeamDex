package com.main.teamdex


data class Equipo(
    val id: Int,
    var listaPokemon: MutableList<Pokemon>,
    val autor: Int,
    var autorNom: String,
    val nombre: String,
)