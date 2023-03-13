package com.borispuhacin.pokedexpokemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.borispuhacin.pokedexpokemon.util.Converters

@Database(entities = [SavedPokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class SavedPokemonDatabase : RoomDatabase() {

    abstract fun savedPokemonDao(): SavedPokemonDao
}