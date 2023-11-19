package com.pdm.pokemon_retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm.pokemon_retrofit.R
import com.pdm.pokemon_retrofit.domain.Pokemon
import com.pdm.pokemon_retrofit.domain.PokemonType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rvPokemon)

        val charmander = Pokemon(
            "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png",
            1,
            "Charmander",
            listOf<PokemonType>(
                PokemonType("Fire")
            )
        )
        val pokemons = listOf<Pokemon>(
            charmander, charmander, charmander, charmander, charmander
        )
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}