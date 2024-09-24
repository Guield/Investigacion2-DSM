package com.example.pokeapi
import android.annotation.SuppressLint
import android.os.Bundle
import  android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokeapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pokeAdapter: PokeAdapter
    private var fullPokeList: MutableList<PokeDetailsResponse> = mutableListOf()
    private var displayedPokeList: MutableList<PokeDetailsResponse> = mutableListOf()

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        fetchAllPokemons()

        binding.searchPokemon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterPokemonList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterPokemonList(newText)
                return true
            }
        })
    }

    private fun initRecyclerView() {
        pokeAdapter = PokeAdapter(displayedPokeList)
        binding.listPokemon.layoutManager = LinearLayoutManager(this)
        binding.listPokemon.adapter = pokeAdapter
    }

    private fun fetchAllPokemons() {
        apiService.getAllPokemons().enqueue(object : Callback<PokeListResponse> {
            override fun onResponse(call: Call<PokeListResponse>, response: Response<PokeListResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.results.forEachIndexed { index, pokemon ->
                        fetchPokemonDetails(index + 1) // Se obtiene el ID del Pokémon desde el índice
                    }
                }
            }
            override fun onFailure(call: Call<PokeListResponse>, t: Throwable) {
                showError()
            }
        })
    }

    private fun fetchPokemonDetails(id: Int) {
        apiService.getPokemonDetails(id).enqueue(object : Callback<PokeDetailsResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<PokeDetailsResponse>, response: Response<PokeDetailsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    fullPokeList.add(response.body()!!)
                    displayedPokeList.add(response.body()!!)
                    pokeAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<PokeDetailsResponse>, t: Throwable) {
                showError()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterPokemonList(query: String?) {
        displayedPokeList.clear()
        if (query.isNullOrEmpty()) {
            displayedPokeList.addAll(fullPokeList)
        } else {
            val filteredList = fullPokeList.filter { it.name.contains(query, ignoreCase = true) }
            displayedPokeList.addAll(filteredList)
        }
        pokeAdapter.notifyDataSetChanged()
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}
