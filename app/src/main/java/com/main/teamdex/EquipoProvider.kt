package com.main.teamdex

import android.content.Context

class EquipoProvider {
    companion object{
        var listaEquipo = mutableListOf(
            Equipo(1,intArrayOf(3,14,74,308,151,43), mutableListOf<Pokemon>(),"Joaquin Dominguez", "Equipazo",""),
            Equipo(2,intArrayOf(6,18,88,31,130,61),mutableListOf<Pokemon>(),"Pepe Botella", "MiEquipo",""),
            Equipo(3,intArrayOf(9,11,63,45,120,150),mutableListOf<Pokemon>(),"Paco Perez", "MejorEquipo","")
        )

        var listaFavEquipo = mutableListOf<Equipo>()

        public fun rellenaLista(lan : String){
            listaEquipo.forEach{
                for(i in 0..5) {
                    it.listaId?.let { it1 ->
                        Conector.getPokemon({ pokemon: Pokemon ->
                            println(pokemon.nombre)
                            it.listaPokemon.add(pokemon)
                        }, it1.get(i), lan)
                    }
                }
            }
        }

        public fun rellenaListaFav(cont:Context){
            listaFavEquipo.clear()

            val data = LocalDatabase.getDatabase(cont)
            val favDAO = data.favoritosDao()

            val listaFavId = favDAO.getAllFav()

            listaFavId?.forEach { it1 ->
                var id = it1.id
                listaEquipo.forEach {
                    if(it.id==id){
                        listaFavEquipo.add(it)
                    }
                }
            }


        }
    }
}


