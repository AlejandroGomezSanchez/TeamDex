package com.main.teamdex.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.main.teamdex.ConectorAPI
import com.main.teamdex.Equipo
import com.main.teamdex.LoginActivity
import com.main.teamdex.MainActivity
import com.main.teamdex.R
import com.main.teamdex.databinding.ItemEquipoBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Exception

class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemEquipoBinding.bind(itemView)

    fun bind(equipoModel: Equipo, nav: NavController){
        binding.nombre.text = equipoModel.nombre
        binding.autor.text = equipoModel.autorNom


        var listaSprites: MutableList<String> = mutableListOf()


        CoroutineScope(Dispatchers.Main).launch {
            loadSprites(equipoModel)
        }

        val bundle : Bundle = Bundle()
        bundle.putInt("equipo", equipoModel.id)

        itemView.setOnClickListener {
            try {
                nav.navigate(R.id.action_itemListFragment2_to_detailItemFragment2,bundle)
            }catch (exe : Exception){
                nav.navigate(R.id.action_favItemListFragment2_to_detailFavItemFragment2,bundle)
            }

        }



    }

    suspend fun loadSprites(equipoModel: Equipo) {
        val listaSprites = mutableListOf<String>()

        // Descargar los sprites en un coroutine en el contexto IO
        withContext(Dispatchers.IO) {
            for (i in 1..6) {
                listaSprites.add(ConectorAPI.getSprite(equipoModel.listaPokemon[i-1].num,equipoModel.listaPokemon[i-1].shiny))
            }
        }

        // Cargar las imágenes en los ImageView después de que todos los sprites se hayan descargado
        withContext(Dispatchers.Main) {
            Picasso.get().load(listaSprites[0]).into(binding.pk1)
            Picasso.get().load(listaSprites[1]).into(binding.pk2)
            Picasso.get().load(listaSprites[2]).into(binding.pk3)
            Picasso.get().load(listaSprites[3]).into(binding.pk4)
            Picasso.get().load(listaSprites[4]).into(binding.pk5)
            Picasso.get().load(listaSprites[5]).into(binding.pk6)
        }
    }
}