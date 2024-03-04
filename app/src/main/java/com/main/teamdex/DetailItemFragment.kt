package com.main.teamdex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.main.teamdex.databinding.FragmentCreditBinding
import com.main.teamdex.databinding.FragmentDetailItemBinding
import com.squareup.picasso.Picasso

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

        val equipo = arguments?.getParcelable<Equipo>("equipo")

        if (equipo != null) {
            for (i in 0 until minOf(6, equipo.listaPokemon.size)) {
                val pokemon = equipo.listaPokemon[i]

                Picasso.get()
                    .load(pokemon.sprite)
                    .into(when (i) {
                        0 -> binding.poke1
                        1 -> binding.poke2
                        2 -> binding.poke3
                        3 -> binding.poke4
                        4 -> binding.poke5
                        5 -> binding.poke6
                        else -> throw IndexOutOfBoundsException("Index $i is out of bounds for equipo.listaPokemon")
                    })

                when (i) {
                    0 -> binding.nom1
                    1 -> binding.nom2
                    2 -> binding.nom3
                    3 -> binding.nom4
                    4 -> binding.nom5
                    5 -> binding.nom6
                    else -> throw IndexOutOfBoundsException("Index $i is out of bounds for equipo.listaPokemon")
                }.text = pokemon.nombre.replaceFirst(pokemon.nombre[0],pokemon.nombre[0].uppercaseChar())

                when (i) {
                    0 -> binding.tipos1
                    1 -> binding.tipos2
                    2 -> binding.tipos3
                    3 -> binding.tipos4
                    4 -> binding.tipos5
                    5 -> binding.tipos6
                    else -> throw IndexOutOfBoundsException("Index $i is out of bounds for equipo.listaPokemon")
                }.text = getString(R.string.tipos)+pokemon.tipo1 + pokemon.tipo2

                when (i) {
                    0 -> binding.hab1
                    1 -> binding.hab2
                    2 -> binding.hab3
                    3 -> binding.hab4
                    4 -> binding.hab5
                    5 -> binding.hab6
                    else -> throw IndexOutOfBoundsException("Index $i is out of bounds for equipo.listaPokemon")
                }.text = getString(R.string.habilidad)+ pokemon.habilidad
            }

            binding.nombreE.text = equipo.nombre

        }

        return binding.root
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