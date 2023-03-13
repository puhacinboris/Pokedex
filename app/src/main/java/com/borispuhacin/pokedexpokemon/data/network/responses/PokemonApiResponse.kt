package com.borispuhacin.pokedexpokemon.data.network.responses


import com.google.gson.annotations.SerializedName

data class PokemonApiResponse(
    @SerializedName("pokemonApiResults")
    val results: List<PokemonApiResult>
)