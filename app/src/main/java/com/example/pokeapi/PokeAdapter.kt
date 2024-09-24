package com.example.pokeapi

import  android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PokeAdapter(private val pokeList: List<PokeDetailsResponse>) : RecyclerView.Adapter<PokeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poke, parent, false)
        return PokeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bind(pokeList[position])

    }

    override fun getItemCount(): Int = pokeList.size
}
