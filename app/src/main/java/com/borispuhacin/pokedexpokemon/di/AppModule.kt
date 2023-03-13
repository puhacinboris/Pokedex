package com.borispuhacin.pokedexpokemon.di

import android.app.Application
import androidx.room.Room
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemonDao
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemonDatabase
import com.borispuhacin.pokedexpokemon.data.network.PokemonApi
import com.borispuhacin.pokedexpokemon.repository.PokemonRepository
import com.borispuhacin.pokedexpokemon.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory()).build()
                )
            )
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSavedPokemonDatabase(app: Application) =
        Room.databaseBuilder(app, SavedPokemonDatabase::class.java, "saved_pokemon_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSavedPokemonDao(db: SavedPokemonDatabase) = db.savedPokemonDao()

    @Singleton
    @Provides
    fun providePokemonRepository(pokemonApi: PokemonApi, savedPokemonDao: SavedPokemonDao) =
        PokemonRepository(pokemonApi, savedPokemonDao)
}