package com.main.teamdex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.main.teamdex.databinding.FragmentCreditBinding
import com.main.teamdex.databinding.FragmentDetailItemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.InstantSource

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentDetailItemBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailItemBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("equipo")
        var equipo: Equipo = Equipo(0, mutableListOf(), 0, "", "")

        CoroutineScope(Dispatchers.IO).launch {
            println("hilo")
            if (id != null) {
                equipo = ConectorDB.getTeam( id)
                loadSprites(equipo)
            } else {
                println("juan")
            }

            withContext(Dispatchers.Main) {
                binding.nombreE.text = equipo.nombre
            }

            for (i in 0 until minOf(6, equipo.listaPokemon.size)) {
                val pokemon = equipo.listaPokemon[i]
                println(pokemon.habilidad)

                val nombre = ConectorAPI.getNombre(pokemon.num)
                val tipos = ConectorAPI.getTipos(pokemon.num)

                withContext(Dispatchers.Main) {
                    when (i) {
                        0 -> {
                            binding.nom1.text = nombre.replaceFirstChar { it.uppercaseChar() }
                            binding.tipos1.text = getString(R.string.tipos) + tipos
                            binding.hab1.text = getString(R.string.habilidad) + pokemon.habilidad
                        }
                        1 -> {
                            binding.nom2.text = nombre.replaceFirstChar { it.uppercaseChar() }
                            binding.tipos2.text = getString(R.string.tipos) + tipos
                            binding.hab2.text = getString(R.string.habilidad) + pokemon.habilidad
                        }
                        2 -> {
                            binding.nom3.text = nombre.replaceFirstChar { it.uppercaseChar() }
                            binding.tipos3.text = getString(R.string.tipos) + tipos
                            binding.hab3.text = getString(R.string.habilidad) + pokemon.habilidad
                        }
                        3 -> {
                            binding.nom4.text = nombre.replaceFirstChar { it.uppercaseChar() }
                            binding.tipos4.text = getString(R.string.tipos) + tipos
                            binding.hab4.text = getString(R.string.habilidad) + pokemon.habilidad
                        }
                        4 -> {
                            binding.nom5.text = nombre.replaceFirstChar { it.uppercaseChar() }
                            binding.tipos5.text = getString(R.string.tipos) + tipos
                            binding.hab5.text = getString(R.string.habilidad) + pokemon.habilidad
                        }
                        5 -> {
                            binding.nom6.text = nombre.replaceFirstChar { it.uppercaseChar() }
                            binding.tipos6.text = getString(R.string.tipos) + tipos
                            binding.hab6.text = getString(R.string.habilidad) + pokemon.habilidad
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private suspend fun loadSprites(equipoModel: Equipo) {
        val listaSprites = mutableListOf<String>()

        // Descargar los sprites en un coroutine en el contexto IO
        withContext(Dispatchers.IO) {
            for (i in 1..6) {
                listaSprites.add(ConectorAPI.getSprite(equipoModel.listaPokemon[i-1].num,equipoModel.listaPokemon[i-1].shiny))
            }
        }

        // Cargar las imágenes en los ImageView después de que todos los sprites se hayan descargado
        withContext(Dispatchers.Main) {
            Picasso.get().load(listaSprites[0]).into(binding.poke1)
            Picasso.get().load(listaSprites[1]).into(binding.poke2)
            Picasso.get().load(listaSprites[2]).into(binding.poke3)
            Picasso.get().load(listaSprites[3]).into(binding.poke4)
            Picasso.get().load(listaSprites[4]).into(binding.poke5)
            Picasso.get().load(listaSprites[5]).into(binding.poke6)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}