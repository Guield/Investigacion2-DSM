package com.example.pokeapi


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //Lastimosamente la API que utilizamos no contaba con un metodo POST
    // para poder utilizarlo solamente el metodo GET
    @GET("pokemon?limit=251")
    fun getAllPokemons(): Call<PokeListResponse>
    //El ID no es un numero sino el nombre del pokemon
    @GET("pokemon/{id}")
    fun getPokemonDetails(@Path("id") id: Int): Call<PokeDetailsResponse>
}



