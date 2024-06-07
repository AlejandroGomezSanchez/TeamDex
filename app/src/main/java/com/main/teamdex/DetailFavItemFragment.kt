package com.main.teamdex

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonSyntaxException
import com.main.teamdex.databinding.FragmentDetailFavItemBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFavItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFavItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentDetailFavItemBinding? = null
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
    ): View {
        _binding = FragmentDetailFavItemBinding.inflate(inflater, container, false)

        binding.hab1.visibility = View.INVISIBLE
        binding.hab2.visibility = View.INVISIBLE
        binding.hab3.visibility = View.INVISIBLE

        binding.hab1.performClick()
        val slot = arguments?.getInt("slot")

        val adapter = this.context?.let {
            ArrayAdapter(
                it,
                R.layout.simple_list_item_1, EquipoProvider.listaPokemons
            )
        }

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            CoroutineScope(Dispatchers.IO).launch {
                loadDatos(selectedItem)
            }
            binding.habilidad.visibility = View.VISIBLE
            binding.shiny.visibility = View.VISIBLE
        }

        binding.shiny.setOnCheckedChangeListener { _, _ ->

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (ConectorAPI.getPokeNum(binding.autoCompleteTextView.text.toString())!=0) {
                        loadDatos(binding.autoCompleteTextView.text.toString())
                    }
                }catch (e:JsonSyntaxException){

                }

            }

        }

        binding.guardar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (slot != null) {
                    guardarPoke(slot)
                }
            }
        }

        binding.autoCompleteTextView.setAdapter(adapter)

        return binding.root
    }

    private suspend fun loadDatos(nom: String) {
        val listaHab: List<String>
        val sprite: String

        // Descargar los sprites en un coroutine en el contexto IO
        withContext(Dispatchers.IO) {
            sprite = if (binding.shiny.isChecked)
                ConectorAPI.getSprite(nom, 1)
            else {
                ConectorAPI.getSprite(nom, 0)
            }
            listaHab = ConectorAPI.getAbilities(nom)
        }

        // Cargar las imágenes en los ImageView después de que todos los sprites se hayan descargado
        withContext(Dispatchers.Main) {
            Picasso.get().load(sprite).into(binding.imageView3)

            binding.hab1.performClick()
            // Manejar diferentes casos basados en la longitud de listaHab
            when (listaHab.size) {
                1 -> {
                    binding.hab1.visibility = View.VISIBLE
                    binding.hab2.visibility = View.INVISIBLE
                    binding.hab3.visibility = View.INVISIBLE

                    binding.hab1.text = listaHab[0]
                }

                2 -> {
                    binding.hab1.visibility = View.VISIBLE
                    binding.hab3.visibility = View.INVISIBLE
                    binding.hab2.visibility = View.VISIBLE
                    binding.hab1.text = listaHab[0]
                    binding.hab2.text = listaHab[1]
                }

                3 -> {
                    binding.hab1.visibility = View.VISIBLE
                    binding.hab3.visibility = View.VISIBLE
                    binding.hab2.visibility = View.VISIBLE
                    binding.hab1.text = listaHab[0]
                    binding.hab2.text = listaHab[1]
                    binding.hab3.text = listaHab[2]
                }

                else -> {

                }
            }
        }
    }

    private suspend fun guardarPoke(slot: Int){
        var numPoke = 0
        val hab: String

        withContext(Dispatchers.IO) {
            try {
                numPoke = ConectorAPI.getPokeNum(binding.autoCompleteTextView.text.toString())
            }catch (e: JsonSyntaxException){

            }

            hab = if(binding.hab1.isChecked){
                binding.hab1.text.toString()
            }else if(binding.hab2.isChecked){
                binding.hab2.text.toString()
            }else {
                binding.hab3.text.toString()
            }
        }

        withContext(Dispatchers.Main) {
            if (numPoke!=0){
                val pokemon = Pokemon(0,numPoke,hab,binding.item.text.toString(),if (binding.shiny.isChecked){1}else{0})
                val id : Int

                withContext(Dispatchers.IO) {
                    id = ConectorDB.addPokemon(pokemon)
                    FavItemListFragment.team[slot-1]=id
                }

                // Mueve esta parte dentro de Dispatchers.Main
                findNavController().navigate(com.main.teamdex.R.id.action_detailFavItemFragment2_to_favItemListFragment2)
            }
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFavItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFavItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}