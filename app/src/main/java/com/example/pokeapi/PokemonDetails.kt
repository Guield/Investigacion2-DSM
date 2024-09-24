package com.example.pokeapi

import com.google.gson.annotations.SerializedName

data class PokeDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("types") val types: List<Type>
)

data class Type(
    @SerializedName("type") val type: TypeInfo
)

data class TypeInfo(
    @SerializedName("name") val name: String
)
