package com.borispuhacin.pokedexpokemon.ui.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemon
import com.borispuhacin.pokedexpokemon.data.network.responses.Pokemon
import com.borispuhacin.pokedexpokemon.data.network.responses.PokemonApiResult
import com.borispuhacin.pokedexpokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    init {
        getAllPokemon()
    }

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonList = MutableLiveData<List<PokemonApiResult>>()
    val pokemonList: LiveData<List<PokemonApiResult>> = _pokemonList

    private fun getAllPokemon() {
        viewModelScope.launch {
            try {
                val result = repository.getAllPokemon(1279)
                _pokemonList.value = result.results
            } catch (e: Exception) {
                Log.e("TAG", "${e.message}")
            }
        }
    }

    fun getPokemon(name: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPokemon(name)
                _pokemon.value = result
            } catch (e: Exception) {
                Log.e("TAG", "${e.message}")
            }
        }
    }

    fun savePokemon(pokemon: SavedPokemon) {
        viewModelScope.launch {
            try {
                repository.savePokemon(pokemon)
            } catch (e: Exception) {
                Log.e("TAG", "${e.message}")
            }
        }
    }

    fun deletePokemon(pokemon: SavedPokemon) {
        viewModelScope.launch {
            try {
                repository.deletePokemon(pokemon)
            } catch (e: Exception) {
                Log.e("TAG", "${e.message}")
            }
        }
    }

    fun deleteAllPokemon() {
        viewModelScope.launch {
            try {
                repository.deleteAllPokemon()
            } catch (e: Exception) {
                Log.e("TAG", "${e.message}")
            }
        }
    }

    val getAllSaved = repository.getAllSaved().asLiveData()
}