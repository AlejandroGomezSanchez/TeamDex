package com.main.teamdex

class EquipoProvider {
    companion object{
        var listaEquipo = List<Equipo>(3){
            Equipo(intArrayOf(3,14,74,23,77,43), mutableListOf<Pokemon>(),false,"Joaquin Dominguez", "Equipazo")
            Equipo(intArrayOf(6,18,88,31,130,61),mutableListOf<Pokemon>(),false,"Pepe Botella", "MiEquipo")
            Equipo(intArrayOf(9,11,63,45,120,150),mutableListOf<Pokemon>(),false,"Paco Perez", "MejorEquipo")
        }

        public fun rellenaLista(){
            listaEquipo.forEach{
                for(i in 1..6) {
                    Conector.getPokemon({ pokemon: Pokemon ->
                        println(pokemon.nombre)
                        it.listaPokemon.add(pokemon)
                    }, it.listaId[i])
                }
            }
        }
    }
}


