package com.pdm.pokemon_retrofit.domain

data class Pokemon (
    val imageUrl: String,
    val number: Int,
    val name: String,
    val types: List<PokemonType>
) {
    val formattedNumer = number.toString().padStart(3, '0')
}

