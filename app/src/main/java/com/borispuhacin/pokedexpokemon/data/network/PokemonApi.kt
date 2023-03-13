package com.borispuhacin.pokedexpokemon.data.network

import com.borispuhacin.pokedexpokemon.data.network.responses.Pokemon


import com.borispuhacin.pokedexpokemon.data.network.responses.PokemonApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface PokemonApi {

    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int
    ) : PokemonApiResponse

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String
    ) : Pokemon

}