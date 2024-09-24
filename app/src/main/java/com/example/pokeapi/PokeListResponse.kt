package com.example.pokeapi

import com.google.gson.annotations.SerializedName

data class PokeListResponse(
    @SerializedName("results") val results: List<Pokemon>
)

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
