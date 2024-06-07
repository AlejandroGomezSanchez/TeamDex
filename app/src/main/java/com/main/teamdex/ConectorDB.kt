package com.main.teamdex

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class ConectorDB {

    companion object {
        private const val url =
            "jdbc:mysql://b7ecuobptk8hdq9eug1p-mysql.services.clever-cloud.com:3306/b7ecuobptk8hdq9eug1p"
        private const val user = "un7izsyt7tpffnat"
        private const val password = "oBBNzkClEz7g64mHzG6M"

        private lateinit var conex: Connection

        fun connectToDatabase() {
           try {
                conex = DriverManager.getConnection(url, user, password)
            } catch (e: SQLException) {
                e.printStackTrace()
                null
            }
        }

        fun getNombreUsu( id: Int): String {
            var nombre = ""
            val sql = "SELECT nombre FROM usuarios WHERE id = ?"
            var preparedStatement: PreparedStatement? = null
            var resultSet: ResultSet? = null

            try {
                preparedStatement = conex.prepareStatement(sql)
                preparedStatement?.setInt(1, id)
                resultSet = preparedStatement?.executeQuery()

                if (resultSet != null && resultSet.next()) {
                    nombre = resultSet.getString("nombre")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                resultSet?.close()
                preparedStatement?.close()
            }

            return nombre
        }

        fun añadeUsuario(usu: String, cont: String): Boolean {

            var resul = true
            val thread = Thread {
                val sql = "INSERT INTO usuarios(nombre, contrasena) VALUES(?, ?)"
                try {
                    val preparedStatement = conex.prepareStatement(sql)
                    preparedStatement?.setString(1, usu)
                    preparedStatement?.setString(2, cont)
                    preparedStatement?.executeUpdate()
                } catch (e: SQLException) {
                    e.printStackTrace()
                    resul = false
                    // Aquí puedes manejar la excepción de otra manera si lo prefieres
                }
            }
            thread.start()
            thread.join() // Espera a que el hilo termine antes de continuar

            return resul
        }


        fun login( usu: String, cont: String): Int {
            var id = 0
            val thread = Thread {
                val sql = "SELECT * FROM usuarios WHERE nombre = ?"
                val preparedStatement = conex.prepareStatement(sql)
                preparedStatement?.setString(1, usu)
                val resultSet = preparedStatement?.executeQuery()

                if (resultSet != null) {
                    while (resultSet.next()) {
                        if (resultSet.getString("contrasena") == cont) {
                            id = resultSet.getInt("id")
                        }
                    }
                }
            }
            thread.start()
            thread.join() // Espera a que el hilo termine antes de retornar la conexión

            return id
        }

        fun getPokemons(id: Int): MutableList<Pokemon> {
            val lista = mutableListOf<Pokemon>()
            val sql =
                "SELECT poke1, poke2, poke3, poke4, poke5, poke6 FROM equipo WHERE idequipo = ?"
            var preparedStatement: PreparedStatement? = null
            var resultSet: ResultSet? = null

            try {
                preparedStatement = conex.prepareStatement(sql)
                preparedStatement?.setInt(1, id)
                resultSet = preparedStatement?.executeQuery()

                if (resultSet != null && resultSet.next()) {
                    val pokemons = listOf(
                        resultSet.getInt("poke1"),
                        resultSet.getInt("poke2"),
                        resultSet.getInt("poke3"),
                        resultSet.getInt("poke4"),
                        resultSet.getInt("poke5"),
                        resultSet.getInt("poke6")
                    )

                    for (pokeId in pokemons) {
                        if (pokeId != 0) {
                            lista.add(getPokemon(pokeId))
                        }
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                resultSet?.close()
                preparedStatement?.close()
            }

            return lista
        }

        fun getTeams(): List<Equipo> {
            val equipos = mutableListOf<Equipo>()
            val sql = "SELECT * FROM equipo"
            var preparedStatement: PreparedStatement? = null
            var resultSet: ResultSet? = null

            try {
                preparedStatement = conex.prepareStatement(sql)
                resultSet = preparedStatement?.executeQuery()

                if (resultSet != null) {
                    while (resultSet.next()) {
                        val equipo = Equipo(
                            resultSet.getInt("idequipo"),
                            mutableListOf(),
                            resultSet.getInt("idusuario"),
                            "",
                            resultSet.getString("nombre")
                        )
                        equipo.listaPokemon = getPokemons(equipo.id)
                        equipo.autorNom = getAutorNombre(equipo.id)
                        equipos.add(equipo)
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                resultSet?.close()
                preparedStatement?.close()
            }

            return equipos
        }

        private fun getAutorNombre(equipoId: Int): String {
            val sql =
                "SELECT u.nombre AS nombre_usuario FROM equipo e JOIN usuarios u ON e.idusuario = u.id WHERE e.idequipo = ?"
            var preparedStatement: PreparedStatement? = null
            var resultSet: ResultSet? = null
            var autorNombre = ""

            try {
                preparedStatement = conex.prepareStatement(sql)
                preparedStatement?.setInt(1, equipoId)
                resultSet = preparedStatement?.executeQuery()

                if (resultSet != null && resultSet.next()) {
                    autorNombre = resultSet.getString("nombre_usuario")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                resultSet?.close()
                preparedStatement?.close()
            }

            return autorNombre
        }

        fun getTeam(id: Int): Equipo {
            var equipo = Equipo(0, mutableListOf(), 0, "", "")
            val sql = "SELECT * FROM equipo WHERE idequipo = ?"
            var preparedStatement: PreparedStatement? = null
            var resultSet: ResultSet? = null

            try {
                preparedStatement = conex?.prepareStatement(sql)
                preparedStatement?.setInt(1, id)
                resultSet = preparedStatement?.executeQuery()

                if (resultSet != null && resultSet.next()) {
                    equipo = Equipo(
                        resultSet.getInt("idequipo"),
                        getPokemons(id),
                        resultSet.getInt("idusuario"),
                        getAutorNombre(id),
                        resultSet.getString("nombre")
                    )
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                resultSet?.close()
                preparedStatement?.close()
            }

            return equipo
        }

        fun getPokemon(id: Int): Pokemon {
            var pokemon = Pokemon(0, 0, "", "", 0)
            val sql = "SELECT * FROM pokemon WHERE idpokemon = ?"
            var preparedStatement: PreparedStatement? = null
            var resultSet: ResultSet? = null

            try {
                preparedStatement = conex.prepareStatement(sql)
                preparedStatement?.setInt(1, id)
                resultSet = preparedStatement?.executeQuery()

                if (resultSet != null && resultSet.next()) {
                    pokemon = Pokemon(
                        resultSet.getInt("idpokemon"),
                        resultSet.getInt("numpoke"),
                        resultSet.getString("ability"),
                        resultSet.getString("item"),
                        resultSet.getInt("shiny")
                    )
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                resultSet?.close()
                preparedStatement?.close()
            }

            return pokemon
        }

        fun addPokemon(pokemon: Pokemon): Int {
            var id = 0
            var sql = "INSERT INTO pokemon(numpoke, ability, item, shiny) VALUES(?, ?, ?, ?)"
            try {
                var preparedStatement = conex.prepareStatement(sql)
                preparedStatement?.setInt(1, pokemon.num)
                preparedStatement?.setString(2, pokemon.habilidad)
                preparedStatement?.setString(3, pokemon.item)
                preparedStatement?.setInt(4, pokemon.shiny)
                preparedStatement?.executeUpdate()

                sql = "SELECT MAX(idpokemon) FROM pokemon"
                preparedStatement = conex.prepareStatement(sql)
                val resultSet = preparedStatement?.executeQuery()

                if (resultSet != null) {
                    if (resultSet.next()) {
                        id = resultSet.getInt("MAX(idpokemon)")
                    }
                    resultSet.close()
                }
                preparedStatement?.close()


            } catch (e: SQLException) {
                e.printStackTrace()
                // Aquí puedes manejar la excepción de otra manera si lo prefieres
            }
            return id
        }

        fun addTeam(equipo: Equipo) {

            var sql =
                "INSERT INTO equipo(idusuario, nombre, poke1, poke2, poke3, poke4, poke5, poke6) VALUES(?, ?, ?, ?,?,?,?,?)"

            var preparedStatement = conex.prepareStatement(sql)
            preparedStatement?.setInt(1, LoginActivity.usuarioId)
            preparedStatement?.setString(2, equipo.nombre)
            preparedStatement?.setInt(3, equipo.listaPokemon[0].id)
            preparedStatement?.setInt(4, equipo.listaPokemon[1].id)
            preparedStatement?.setInt(5, equipo.listaPokemon[2].id)
            preparedStatement?.setInt(6, equipo.listaPokemon[3].id)
            preparedStatement?.setInt(7, equipo.listaPokemon[4].id)
            preparedStatement?.setInt(8, equipo.listaPokemon[5].id)
            preparedStatement?.executeUpdate()


        }

    }
    }