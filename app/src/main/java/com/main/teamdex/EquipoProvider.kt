package com.main.teamdex

class EquipoProvider {
    companion object{
        lateinit var listaEquipo : List<Equipo>

        lateinit var listaPokemons : List<String>


        public fun rellenaNombres(){
            listaPokemons = ConectorAPI.getAllNombres()
        }

        public fun rellenaLista(){

            listaEquipo = ConectorDB.getTeams()

        }
    }
}



