package com.main.teamdex.adapter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.main.teamdex.Equipo
import com.main.teamdex.R
import com.main.teamdex.databinding.ItemEquipoBinding
import com.squareup.picasso.Picasso
import java.lang.Exception

class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemEquipoBinding.bind(itemView)

    fun bind(equipoModel: Equipo, nav: NavController){
        binding.nombre.text = equipoModel.nombre
        binding.autor.text = equipoModel.autor

        if(equipoModel.fav){
            binding.fav.text= "Quitar Favorito"
        }else{
            binding.fav.text= "Favorito"
        }

        Picasso.get()
            .load(equipoModel.listaPokemon[0].sprite)
            .into(binding.pk1)
        Picasso.get()
            .load(equipoModel.listaPokemon[1].sprite)
            .into(binding.pk2)
        Picasso.get()
            .load(equipoModel.listaPokemon[2].sprite)
            .into(binding.pk3)
        Picasso.get()
            .load(equipoModel.listaPokemon[3].sprite)
            .into(binding.pk4)
        Picasso.get()
            .load(equipoModel.listaPokemon[4].sprite)
            .into(binding.pk5)
        Picasso.get()
            .load(equipoModel.listaPokemon[5].sprite)
            .into(binding.pk6)
        binding.fav.setOnClickListener {
            if (equipoModel.fav){
                binding.fav.text = "Favorito"
                equipoModel.fav = false
            }else{
                binding.fav.text = "Quitar Favorito"
                equipoModel.fav = true
            }
        }
        val bundle : Bundle = Bundle()
        bundle.putParcelable("equipo",equipoModel)

        itemView.setOnClickListener {
            try {
                nav.navigate(R.id.action_itemListFragment_to_detailItemFragment,bundle)
            }catch (exe : Exception){
                nav.navigate(R.id.action_favItemListFragment_to_detailFavItemFragment,bundle)
            }

        }

    }
}