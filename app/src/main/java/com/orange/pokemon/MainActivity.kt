package com.orange.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.orange.pokemon.adapter.pokemonAdapter
import com.orange.pokemon.data.PokemonDao
import com.orange.pokemon.data.PokemonDatabase
import com.orange.pokemon.data.PokemonEntity

import com.orange.pokemon.databinding.ActivityMainBinding
import com.orange.pokemon.model.Pokemon
import com.orange.pokemon.networking.ApiService
import com.orange.pokemon.networking.NetworkClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonAdapter: pokemonAdapter
    private lateinit var database: PokemonDatabase
    private lateinit var dao: PokemonDao
    private lateinit var listOfPokemons: List<Pokemon>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonAdapter = pokemonAdapter()

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = pokemonAdapter
        }
        database = PokemonDatabase.getInstance(this)
        dao = database.getPokemonDao()

        val service = NetworkClient().getRetrofit().create(ApiService::class.java)
        service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {


                if (response.isSuccessful) {

                    listOfPokemons = response.body()!!

                    Log.e(TAG, "onResponse  ${response.body()?.get(0)}")
                    GlobalScope.launch {
                        dao.insertAll(listOfPokemons!!.map {
                            PokemonEntity(
                                baseExp = it.baseExp,
                                name = it.name,
                                cycles = it.cycles,
                                category = it.category,
                                imageurl = it.imageurl,
                            )
                        })
                        load_db_Data()
                    }


                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                load_db_Data()
                Log.e(TAG, "onFailure : ", t)
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()

            }


        })

    }

    private fun load_db_Data() {
        GlobalScope.launch {
            var list = dao.getAll()
            runOnUiThread {
                pokemonAdapter.submitList(list)
            }
        }


    }

}