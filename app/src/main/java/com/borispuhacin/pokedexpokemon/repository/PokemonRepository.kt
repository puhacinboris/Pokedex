package com.borispuhacin.pokedexpokemon.repository


import com.borispuhacin.pokedexpokemon.data.database.SavedPokemon
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemonDao
import com.borispuhacin.pokedexpokemon.data.network.responses.Pokemon
import com.borispuhacin.pokedexpokemon.data.network.PokemonApi

import com.borispuhacin.pokedexpokemon.data.network.responses.PokemonApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val savedPokemonDao: SavedPokemonDao
){

    suspend fun getAllPokemon(limit: Int) : PokemonApiResponse {
        return pokemonApi.getAllPokemon(limit)
    }

    suspend fun getPokemon(name: String) : Pokemon {
        return pokemonApi.getPokemon(name)
    }

    suspend fun savePokemon(pokemon: SavedPokemon) {
        savedPokemonDao.insertPokemon(pokemon)
    }

    suspend fun deletePokemon(pokemon: SavedPokemon) {
        savedPokemonDao.deletePokemon(pokemon)
    }

    fun getAllSaved(): Flow<List<SavedPokemon>> {
        return savedPokemonDao.getAllSavedPokemon()
    }

    suspend fun deleteAllPokemon() {
        savedPokemonDao.deleteAllPokemon()
    }
}