package com.main.teamdex

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConectorDB {

    companion object {
        fun connectToDatabase(): Connection? {
            var connection: Connection? = null

            val thread = Thread {
                val url =
                    "jdbc:mysql://b7ecuobptk8hdq9eug1p-mysql.services.clever-cloud.com:3306/b7ecuobptk8hdq9eug1p"
                val user = "un7izsyt7tpffnat"
                val password = "oBBNzkClEz7g64mHzG6M"

                try {
                    connection = DriverManager.getConnection(url, user, password)
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }

            thread.start()
            thread.join() // Espera a que el hilo termine antes de retornar la conexión

            return connection
        }

        fun añadeUsuario(conex: Connection, usu: String, cont: String) {
            val sql = "INSERT INTO usuarios(nombre, cont) VALUES($usu, $cont)"
            val statement = conex.createStatement()
            statement.execute(sql)
        }

        fun login(conex: Connection?, usu: String, cont: String): Int {
            var id = 0
            val thread = Thread {
                val sql = "SELECT * FROM usuarios WHERE nombre = ?"
                val preparedStatement = conex?.prepareStatement(sql)
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

    }
}