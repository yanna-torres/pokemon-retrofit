package com.pdm.pokemon_retrofit.domain

import java.util.Locale

data class Pokemon (
    val number: Int,
    val name: String,
    val types: List<PokemonType>
) {
    val formattedNumer = number.toString().padStart(3, '0')
    val formattedName = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val imageUrl: String = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${formattedNumer}.png";
}

