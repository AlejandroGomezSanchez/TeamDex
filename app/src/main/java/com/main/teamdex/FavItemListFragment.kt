package com.main.teamdex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.teamdex.adapter.EquipoAdapter
import com.main.teamdex.databinding.FragmentFavItemListBinding
import com.main.teamdex.databinding.FragmentItemListBinding
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
 * Use the [FavItemListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavItemListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentFavItemListBinding? = null
    private val binding
        get() = _binding!!
    lateinit var adapter : EquipoAdapter
    lateinit var layoutManager : RecyclerView.LayoutManager

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

        val listPoke = mutableListOf<Pokemon>()

        _binding = FragmentFavItemListBinding.inflate(inflater, container, false)

        binding.nombreE.setText(nom)

        if (team[0]!=0){
            binding.crear1.visibility=View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                listPoke.add(loadPokemon(team[0],1))
            }
        }
        if (team[1]!=0){
            binding.crear2.visibility=View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                listPoke.add(loadPokemon(team[1],2))
            }
        }
        if (team[2]!=0){
            binding.crear3.visibility=View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                listPoke.add(loadPokemon(team[2],3))
            }
        }
        if (team[3]!=0){
            binding.crear4.visibility=View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                listPoke.add(loadPokemon(team[3],4))
            }
        }
        if (team[4]!=0){
            binding.crear5.visibility=View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                listPoke.add(loadPokemon(team[4],5))
            }
        }
        if (team[5]!=0){
            binding.crear6.visibility=View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                listPoke.add(loadPokemon(team[5],6))
            }
        }


        val bundle : Bundle = Bundle()


        binding.crear1.setOnClickListener{
            nom = binding.nombreE.text.toString()
            bundle.putInt("slot",1)
            findNavController().navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)

        }

        binding.crear2.setOnClickListener{
            nom = binding.nombreE.text.toString()
            bundle.putInt("slot",2)
            findNavController().navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)

        }

        binding.crear3.setOnClickListener{
            nom = binding.nombreE.text.toString()
            bundle.putInt("slot",3)
            findNavController().navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)

        }

        binding.crear4.setOnClickListener{
            nom = binding.nombreE.text.toString()
            bundle.putInt("slot",4)
            findNavController().navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)

        }

        binding.crear5.setOnClickListener{
            nom = binding.nombreE.text.toString()
            bundle.putInt("slot",5)
            findNavController().navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)

        }

        binding.crear6.setOnClickListener{
            nom = binding.nombreE.text.toString()
            bundle.putInt("slot",6)
            findNavController().navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)

        }

        binding.borrar.setOnClickListener{
            // Borra los datos de la variable team y reinicia el fragmento
            team = intArrayOf(0, 0, 0, 0, 0, 0)
            findNavController().navigate(R.id.action_favItemListFragment2_self)
        }

        binding.publicar.setOnClickListener{

            var comp = true

            for(id in team){
                if(id == 0) {comp = false}
            }

            if(comp) {
                CoroutineScope(Dispatchers.Main).launch {
                    publicarEquipo(listPoke)
                }
            }
        }


        return binding.root
    }

    private  suspend fun publicarEquipo(listPoke: MutableList<Pokemon>){

        val equipo: Equipo
        val nombreUsu: String


        withContext(Dispatchers.IO) {
            nombreUsu = ConectorDB.getNombreUsu(LoginActivity.usuarioId)
            equipo= Equipo(0, listPoke.toMutableList(),LoginActivity.usuarioId,nombreUsu,binding.nombreE.text.toString())
            ConectorDB.addTeam(equipo)
            EquipoProvider.rellenaLista()
        }

        withContext(Dispatchers.Main) {

            team = intArrayOf(0, 0, 0, 0, 0, 0)
            findNavController().navigate(R.id.action_favItemListFragment2_self)
        }
    }

    private suspend fun loadPokemon(id: Int, slot: Int): Pokemon{

        val pokemon : Pokemon


        // Descargar los sprites en un coroutine en el contexto IO
        withContext(Dispatchers.IO) {
            pokemon = ConectorDB.getPokemon(id)
        }

        // Cargar las imágenes en los ImageView después de que todos los sprites se hayan descargado
        withContext(Dispatchers.Main) {
            val nom : String
            val sprite : String

            withContext(Dispatchers.IO) {
                nom = ConectorAPI.getNombre(pokemon.num)
                sprite = ConectorAPI.getSprite(pokemon.num,pokemon.shiny)
            }

            withContext(Dispatchers.Main) {
                Picasso.get().load(sprite).into(
                    when (slot) {
                        1 -> {
                            binding.img1
                        }
                        2 -> {
                            binding.img2
                        }
                        3 -> {
                            binding.img3
                        }
                        4 -> {
                            binding.img4
                        }
                        5 -> {
                            binding.img5
                        }
                        6 -> {
                            binding.img6
                        }
                        else -> {
                            binding.img1
                        }
                    }
                )

                when (slot){
                    1 -> {
                        binding.nom1.text=nom
                        binding.hab1.text="Habilidad: "+pokemon.habilidad
                        binding.obj1.text="Objeto: "+pokemon.item
                        binding.nom1.visibility=View.VISIBLE
                        binding.hab1.visibility=View.VISIBLE
                        binding.obj1.visibility=View.VISIBLE
                        binding.img1.visibility=View.VISIBLE
                    }
                    2 -> {
                        binding.nom2.text=nom
                        binding.hab2.text="Habilidad: "+pokemon.habilidad
                        binding.obj2.text="Objeto: "+pokemon.item
                        binding.nom2.visibility=View.VISIBLE
                        binding.hab2.visibility=View.VISIBLE
                        binding.obj2.visibility=View.VISIBLE
                        binding.img2.visibility=View.VISIBLE
                    }
                    3 -> {
                        binding.nom3.text=nom
                        binding.hab3.text="Habilidad: "+pokemon.habilidad
                        binding.obj3.text="Objeto: "+pokemon.item
                        binding.nom3.visibility=View.VISIBLE
                        binding.hab3.visibility=View.VISIBLE
                        binding.obj3.visibility=View.VISIBLE
                        binding.img3.visibility=View.VISIBLE
                    }
                    4 -> {
                        binding.nom4.text=nom
                        binding.hab4.text="Habilidad: "+pokemon.habilidad
                        binding.obj4.text="Objeto: "+pokemon.item
                        binding.nom4.visibility=View.VISIBLE
                        binding.hab4.visibility=View.VISIBLE
                        binding.obj4.visibility=View.VISIBLE
                        binding.img4.visibility=View.VISIBLE
                    }
                    5 -> {
                        binding.nom5.text=nom
                        binding.hab5.text="Habilidad: "+pokemon.habilidad
                        binding.obj5.text="Objeto: "+pokemon.item
                        binding.nom5.visibility=View.VISIBLE
                        binding.hab5.visibility=View.VISIBLE
                        binding.obj5.visibility=View.VISIBLE
                        binding.img5.visibility=View.VISIBLE
                    }
                    6 -> {
                        binding.nom6.text=nom
                        binding.hab6.text="Habilidad: "+pokemon.habilidad
                        binding.obj6.text="Objeto: "+pokemon.item
                        binding.nom6.visibility=View.VISIBLE
                        binding.hab6.visibility=View.VISIBLE
                        binding.obj6.visibility=View.VISIBLE
                        binding.img6.visibility=View.VISIBLE
                    }
                    else ->{

                    }
                }
            }


        }

        return pokemon
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavItemListFragment.
         */
        // TODO: Rename and change types and number of parameters

        var nom  = ""
        var team = intArrayOf(0,0,0,0,0,0)

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavItemListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}