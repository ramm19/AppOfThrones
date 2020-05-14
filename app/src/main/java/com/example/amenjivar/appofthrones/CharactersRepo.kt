package com.example.amenjivar.appofthrones

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

const val URL_CHARACTERS = "http://5b8cb3e77366ab0014a29b04.mockapi.io/characters"

object CharactersRepo {
    //val characters: MutableList<Character> = mutableListOf()
//        get() {
//            if (field.isEmpty())
//                field.addAll(dumyCharacters()) //se ocupa field y no el nombre de la variable characters, porque si no se haria un bucle infinito
//            return field
//        } esto sirvio para llenar la app mientras no teniamos datos reales


    private var characters: MutableList<Character> = mutableListOf()

    fun requestCharacters(context: Context,
                          success: ((MutableList<Character>) -> Unit),
                          error: (() -> Unit)) {
        //no puedo retornar aca la lista, porque volveria sincrona la aplicacion y eso no es bueno //existe JsonObjectRequest
        if (characters.isEmpty()) {

            val request = JsonArrayRequest(Request.Method.GET, URL_CHARACTERS, null,
                    { response ->
                        characters = parseCharacters(response)
                        success.invoke(characters)
                    },
                    { volleyError ->
                        error.invoke()
                    })

            Volley.newRequestQueue(context)
                    .add(request)
        } else {
            success.invoke(characters)
        }
    }

    private fun parseCharacters(jsonArray: JSONArray): MutableList<Character> {
        val characters = mutableListOf<Character>()
        for (index in 0..(jsonArray.length() - 1)) {
            val character = parseCharacter(jsonArray.getJSONObject(index))
            characters.add(character)
        }

        return characters
    }

    private fun parseCharacter(jsonCharacter: JSONObject): Character {
        return Character(
                jsonCharacter.getString("id"),
                jsonCharacter.getString("name"),
                jsonCharacter.getString("born"),
                jsonCharacter.getString("title"),
                jsonCharacter.getString("actor"),
                jsonCharacter.getString("quote"),
                jsonCharacter.getString("father"),
                jsonCharacter.getString("mother"),
                jsonCharacter.getString("spouse"),
                jsonCharacter.getString("img"),
                parseHouse(jsonCharacter.getJSONObject("house"))
        )
    }

    private fun parseHouse(jsonHouse: JSONObject): House {
        return House(
                jsonHouse.getString("name"),
                jsonHouse.getString("region"),
                jsonHouse.getString("words"),
                jsonHouse.getString("img")
        )
    }

    private fun dumyCharacters(): MutableList<Character> {
        /*       val dummies : MutableList<Character> =
                       (1..10) .map { index ->
                           intToCharacter(index)
                       }.toMutableList()

               val dummies: MutableList<Character> = mutableListOf()

               for (index in 1..10){
                  val character : Character = Character(
                          name = "Personaje ${index}",
                          title = "Titulo ${index}",
                          born = "Nació en ${index}",
                          actor = "Actor ${index}",
                          quote = "Frase ${index}",
                          father = "Padre ${index}",
                          mother = "Madre ${index}",
                          spouse = "Espos@ ${index}",
                          house = House(
                                  name = "Casa ${index}",
                                  region = "Región ${index}",
                                  words = "Lema ${index}"
                          )
                  )

                   dummies.add(character)

                   return dummies
               }*/

        return (1..10).map {
            intToCharacter(it) //solo sirve para lambdas de un argumento
        }.toMutableList()


    }

    fun findCharacterById(id: String): Character? {
        return characters.find { character ->
            character.id == id
        }
    }

    private fun intToCharacter(index: Int): Character {
        return Character(
                name = "Personaje ${index}",
                title = "Titulo ${index}",
                born = "Nació en ${index}",
                actor = "Actor ${index}",
                quote = "Frase ${index}",
                father = "Padre ${index}",
                mother = "Madre ${index}",
                spouse = "Espos@ ${index}",
                img = "",
                house = dummyHouse()
        )
    }

    private fun dummyHouse(): House {
        val ids = arrayOf("stark", "lannister", "tyrell", "arryn", "baratheon", "tully")
        val randomIdPosition = Random().nextInt(ids.size)

        return House(name = ids[randomIdPosition],
                region = "Región",
                words = "Lema",
                img = "")
    }
}