package com.example.pokeapi

import android.view.View
import  com.squareup.picasso.Picasso
import  androidx.recyclerview.widget.RecyclerView
import com.example.pokeapi.databinding.ItemPokeBinding

class PokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val itemPokeBinding: ItemPokeBinding = ItemPokeBinding.bind(view)

    fun bind(pokemon: PokeDetailsResponse) {
        Picasso.get().load(pokemon.sprites.frontDefault).into(itemPokeBinding.ivPoke)
        itemPokeBinding.tvName.text = pokemon.name.capitalize()
        itemPokeBinding.tvType.text = pokemon.types.joinToString(", ") { it.type.name.capitalize() }
    }
}


