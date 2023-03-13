package com.borispuhacin.pokedexpokemon.data.network.responses

data class Pokemon(
    val name: String,
    val id: Int,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val abilities: List<Ability>,
    val stats: List<Stat>,
    val imageUrl: String? =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
)
