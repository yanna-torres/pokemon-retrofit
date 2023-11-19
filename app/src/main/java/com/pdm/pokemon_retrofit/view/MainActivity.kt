package com.pdm.pokemon_retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.pokemon_retrofit.R
import com.pdm.pokemon_retrofit.api.PokemonRepository
import com.pdm.pokemon_retrofit.domain.Pokemon
import com.pdm.pokemon_retrofit.domain.PokemonType

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var pokemons = emptyList<Pokemon?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.rvPokemon)

        Thread(Runnable {
            loadPokemons()
        }).start();
    }

    private fun loadPokemons() {
        val pokemonsApiResult = PokemonRepository.listPokemons()

        pokemonsApiResult?.results?.let { it ->
            pokemons = it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                            type.type
                        }
                    )
                }
            }

            recyclerView.post {
                loadRecyclerView()
            }
        }
    }

    private fun loadRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons)

    }
}