package com.main.teamdex

class EquipoProvider {
    companion object{
        var listaEquipo = mutableListOf(
            Equipo(intArrayOf(3,14,74,23,77,43), mutableListOf<Pokemon>(),false,"Joaquin Dominguez", "Equipazo"),
            Equipo(intArrayOf(6,18,88,31,130,61),mutableListOf<Pokemon>(),false,"Pepe Botella", "MiEquipo"),
            Equipo(intArrayOf(9,11,63,45,120,150),mutableListOf<Pokemon>(),false,"Paco Perez", "MejorEquipo")
        )

        var listaFavEquipo = mutableListOf<Equipo>()

        public fun rellenaLista(){
            listaEquipo.forEach{
                for(i in 0..5) {
                    it.listaId?.let { it1 ->
                        Conector.getPokemon({ pokemon: Pokemon ->
                            println(pokemon.nombre)
                            it.listaPokemon.add(pokemon)
                        }, it1.get(i))
                    }
                }
            }
        }

        public fun rellenaListaFav(){
            listaFavEquipo.clear()
            listaEquipo.forEach {
                if(it.fav){
                    listaFavEquipo.add(it)
                }
            }
        }

        public fun resetFav(){
            listaFavEquipo.clear()
        }
    }
}


