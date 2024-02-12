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
            Picasso.get()
                .load(equipo.listaPokemon[0].sprite)
                .into(binding.poke1)
            Picasso.get()
                .load(equipo.listaPokemon[1].sprite)
                .into(binding.poke2)
            Picasso.get()
                .load(equipo.listaPokemon[2].sprite)
                .into(binding.poke3)
            Picasso.get()
                .load(equipo.listaPokemon[3].sprite)
                .into(binding.poke4)
            Picasso.get()
                .load(equipo.listaPokemon[4].sprite)
                .into(binding.poke5)
            Picasso.get()
                .load(equipo.listaPokemon[5].sprite)
                .into(binding.poke6)

            binding.nombreE.text = equipo.nombre

            binding.nom1.text = equipo.listaPokemon[0].nombre
            binding.nom2.text = equipo.listaPokemon[1].nombre
            binding.nom3.text = equipo.listaPokemon[2].nombre
            binding.nom4.text = equipo.listaPokemon[3].nombre
            binding.nom5.text = equipo.listaPokemon[4].nombre
            binding.nom6.text = equipo.listaPokemon[5].nombre

            binding.tipos1.text = equipo.listaPokemon[0].tipo1 + equipo.listaPokemon[0].tipo2
            binding.tipos2.text = equipo.listaPokemon[1].tipo1 + equipo.listaPokemon[1].tipo2
            binding.tipos3.text = equipo.listaPokemon[2].tipo1 + equipo.listaPokemon[2].tipo2
            binding.tipos4.text = equipo.listaPokemon[3].tipo1 + equipo.listaPokemon[3].tipo2
            binding.tipos5.text = equipo.listaPokemon[4].tipo1 + equipo.listaPokemon[4].tipo2
            binding.tipos6.text = equipo.listaPokemon[5].tipo1 + equipo.listaPokemon[5].tipo2

            binding.hab1.text = equipo.listaPokemon[0].habilidad
            binding.hab2.text = equipo.listaPokemon[1].habilidad
            binding.hab3.text = equipo.listaPokemon[2].habilidad
            binding.hab4.text = equipo.listaPokemon[3].habilidad
            binding.hab5.text = equipo.listaPokemon[4].habilidad
            binding.hab6.text = equipo.listaPokemon[5].habilidad
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