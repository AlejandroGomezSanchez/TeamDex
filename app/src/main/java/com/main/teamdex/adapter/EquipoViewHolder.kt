package com.main.teamdex.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.main.teamdex.Equipo
import com.main.teamdex.databinding.ItemEquipoBinding
import com.squareup.picasso.Picasso

class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemEquipoBinding.bind(itemView)

    fun bind(equipoModel: Equipo){
        binding.nombre.text = equipoModel.nombre
        binding.autor.text = equipoModel.autor
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
    }
}