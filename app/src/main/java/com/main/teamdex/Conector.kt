package com.main.teamdex

import com.google.gson.JsonParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class Conector {
    companion object {

        val BASE_URL = "https://pokeapi.co/api/v2/"
        val PATH_POKEMON = "/pokemon/"

        fun getPokemon(callback: (Pokemon) -> Unit, id : Int, lan :String) {
            val url = BASE_URL + PATH_POKEMON + id
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

                    val pokemon = Pokemon(id, name, tipo1, tipo2, ability, sprite,shiny)

                    callback(pokemon)
                }
            })
        }
    }
}