package com.main.teamdex

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

class ConectorAPI {


    companion object {

        val BASE_URL = "https://pokeapi.co/api/v2/"
        val PATH_POKEMON = "/pokemon/"

        /*
        fun getPokemon(callback: (Pokemon) -> Unit, num : Int, lan :String) {
            val url = BASE_URL + PATH_POKEMON + num

            val request = Request.Builder().url(url).get().build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle failure
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = JsonParser.parseString(response.body?.string()).asJsonObject

                    val tipos = json.get("types").asJsonArray.size()
                    var tipo2 = ""
                    val tipo1 =
                        json.get("types").asJsonArray.get(0).asJsonObject.get("type").asJsonObject.get("name").asString

                    tipo2 = if (tipos > 1) {
                        " / "+
                                json.get("types").asJsonArray.get(1).asJsonObject.get("type").asJsonObject.get(
                                    "name"
                                ).asString
                    } else {
                        "";
                    }

                    val name = json.get("name").asString

                    val sprite = json.get("sprites").asJsonObject.get("front_default").asString
                    val shiny = json.get("sprites").asJsonObject.get("front_shiny").asString

                    val url =
                        json.get("abilities").asJsonArray.get(0).asJsonObject.get("ability").asJsonObject.get(
                            "url"
                        ).asString
                    val request2 = Request.Builder().url(url).get().build()
                    val response2 = OkHttpClient().newCall(request2).execute()

                    val json2 = JsonParser.parseString(
                        (response2.body?.string() ?: ResponseBody).toString()
                    ).asJsonObject

                    var x: Int = 0




                    for (i in 0..<json2.get("names").asJsonArray.size()) {

                        if (json2.get("names").asJsonArray[i].asJsonObject.get("language").asJsonObject.get(
                                "name"
                            ).asString == lan
                        ) {
                            x = i
                            break

                        }
                    }



                    val ability = json2.get("names").asJsonArray.get(x).asJsonObject.get("name").asString


                    val pokemon = Pokemon(num, name, ability, sprite,shiny)

                    callback(pokemon)
                }
            })


        }
*/

        fun getSprite(num: Int, shiny: Int): String {

            val json = getPokeJson(num)

            return if (shiny == 0) {
                json.get("sprites").asJsonObject.get("front_default").asString
            } else {
                json.get("sprites").asJsonObject.get("front_shiny").asString
            }

        }

        fun getSprite(nom: String, shiny: Int): String {

            val json = getPokeJson(nom)


            return if (shiny == 0) {
                json.get("sprites").asJsonObject.get("front_default").asString
            } else {
                json.get("sprites").asJsonObject.get("front_shiny").asString
            }


        }

        fun getAbilities(nom: String): List<String> {
            val json = getPokeJson(nom)

            val list = mutableListOf<String>()

            val listJson = json.get("abilities").asJsonArray

            for (ability in listJson) {

                val url =
                    ability.asJsonObject.get("ability").asJsonObject.get(
                        "url"
                    ).asString
                val request2 = Request.Builder().url(url).get().build()
                val response2 = OkHttpClient().newCall(request2).execute()

                val json2 = JsonParser.parseString(
                    (response2.body?.string() ?: ResponseBody).toString()
                ).asJsonObject

                var x: Int = 0

                for (i in 0..<json2.get("names").asJsonArray.size()) {

                    if (json2.get("names").asJsonArray[i].asJsonObject.get("language").asJsonObject.get(
                            "name"
                        ).asString == "es"
                    ) {
                        x = i
                        break
                    }
                }

                list.add(json2.get("names").asJsonArray[x].asJsonObject.get("name").asString)
            }

            return list
        }

        private fun getPokeJson(num: Int): JsonObject {
            val BASE_URL = "https://pokeapi.co/api/v2/"
            val PATH_POKEMON = "pokemon/"

            val url = "$BASE_URL$PATH_POKEMON$num"

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            val response: Response = client.newCall(request).execute()

            return JsonParser.parseString(response.body?.string()).asJsonObject
        }

        private fun getPokeJson(nom: String): JsonObject {
            val BASE_URL = "https://pokeapi.co/api/v2/"
            val PATH_POKEMON = "pokemon/"

            val url = "$BASE_URL$PATH_POKEMON$nom"

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            val response: Response = client.newCall(request).execute()

            return JsonParser.parseString(response.body?.string()).asJsonObject
        }

        fun getNombre(num: Int): String {

            val json = getPokeJson(num)

            return json.get("name").asString
        }

        fun getTipos(num: Int): String {

            val json = getPokeJson(num)

            val tipos = json.get("types").asJsonArray.size()
            var tipo2 = ""
            val tipo1 =
                json.get("types").asJsonArray.get(0).asJsonObject.get("type").asJsonObject.get("name").asString

            tipo2 = if (tipos > 1) {
                " / " +
                        json.get("types").asJsonArray.get(1).asJsonObject.get("type").asJsonObject.get(
                            "name"
                        ).asString
            } else {
                "";
            }

            return tipo1 + tipo2
        }

        fun getAllNombres(): List<String> {

            val lista = mutableListOf<String>()

            val url = "https://pokeapi.co/api/v2/pokemon?limit=2000"

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            val response: Response = client.newCall(request).execute()

            val json = JsonParser.parseString(response.body?.string()).asJsonObject

            val jsonLista = json.get("results").asJsonArray

            for (pokemon: JsonElement in jsonLista) {
                lista.add(pokemon.asJsonObject.get("name").asString)
                println(pokemon.asJsonObject.get("name").asString)
            }

            return lista
        }

        fun getPokeNum(nom: String): Int{
            val json = getPokeJson(nom)

            return try {
                json.get("id").asInt
            }catch (e : Exception){
                0
            }

        }
    }
}
