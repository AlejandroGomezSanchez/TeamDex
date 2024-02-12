package com.main.teamdex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.main.teamdex.Equipo
import com.main.teamdex.R

class EquipoAdapter(private val EquipoLista:MutableList<Equipo>) : RecyclerView.Adapter<EquipoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EquipoViewHolder(layoutInflater.inflate(R.layout.item_equipo,parent,false))
    }

    override fun getItemCount(): Int {
        return  EquipoLista.size
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = EquipoLista[position]
        holder.bind(equipo)
    }

}