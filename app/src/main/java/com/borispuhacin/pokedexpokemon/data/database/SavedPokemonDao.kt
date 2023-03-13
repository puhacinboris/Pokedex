package com.borispuhacin.pokedexpokemon.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPokemonDao {

    @Query("SELECT * FROM saved_pokemon")
    fun getAllSavedPokemon(): Flow<List<SavedPokemon>>

    @Query("DELETE FROM saved_pokemon")
    suspend fun deleteAllPokemon()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: SavedPokemon)

    @Delete
    suspend fun deletePokemon(pokemon: SavedPokemon)
}