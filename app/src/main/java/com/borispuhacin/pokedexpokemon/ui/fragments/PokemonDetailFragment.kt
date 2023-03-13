package com.borispuhacin.pokedexpokemon.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.borispuhacin.pokedexpokemon.R
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemon
import com.borispuhacin.pokedexpokemon.data.network.responses.Pokemon
import com.borispuhacin.pokedexpokemon.databinding.FragmentPokemonDetailBinding
import com.borispuhacin.pokedexpokemon.ui.viewModels.SharedViewModel
import com.borispuhacin.pokedexpokemon.util.Utils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: PokemonDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.savedPokemon != null) {
            val pokemon = args.savedPokemon!!
            setupSavedPokemon(pokemon)

        } else {
            sharedViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
                setupPokemon(pokemon)
            }
        }
    }

    private fun setupSavedPokemon(pokemon: SavedPokemon) {
        binding.apply {
            imageViewBack.setOnClickListener {
                findNavController().navigateUp()
            }
            savePokemon.visibility = View.GONE
            releasePokemon.visibility = View.VISIBLE
            textViewDetailsNameNumber.text = pokemon.name
            imageViewDetailsImage.setImageBitmap(pokemon.image)
            val types = mutableListOf<String>()

            if (pokemon.typeTwo.isNullOrBlank()) {
                types.add(pokemon.typeOne)
                cardViewTypeTwo.visibility = View.GONE
            } else {
                types.add(pokemon.typeOne)
                types.add(pokemon.typeTwo)
            }

            textViewWeight.text = getString(R.string.pokemon_weight, pokemon.weight)
            textViewHeight.text = getString(R.string.pokemon_height, pokemon.height)
            textViewPokemonAbilities.text = pokemon.abilities
            Utils.apply {
                calculateDominantColor(pokemon.image, pokemonDetailParentView)
                progressBarSetup(progressBarHp, pokemon.hp)
                progressBarSetup(progressBarAttack, pokemon.attack)
                progressBarSetup(progressBarDefense, pokemon.defense)
                progressBarSetup(progressBarSpecialAttack, pokemon.specialAttack)
                progressBarSetup(progressBarSpecialDefense, pokemon.specialDefense)
                progressBarSetup(progressBarSpeed, pokemon.speed)
                checkNumberOfTypesSavedPokemon(
                    types,
                    textViewTypeOne,
                    textViewTypeTwo,
                    cardViewTypeOne,
                    cardViewTypeTwo,
                    requireActivity()
                )
            }
            releasePokemon.text = getString(R.string.release_pokemon, pokemon.name)
            releasePokemon.setOnClickListener { releasePokemon(pokemon, it) }
        }
    }

    private fun setupPokemon(pokemon: Pokemon) {
        val name = pokemon.name.replaceFirstChar { it.uppercase() }
        val weight = String.format("%.1f", pokemon.weight * 0.1)
        val height = String.format("%.1f", pokemon.height * 0.1)
        val types = pokemon.types
        val stats = pokemon.stats

        val hp = stats[0].base_stat
        val attack = stats[1].base_stat
        val defense = stats[2].base_stat
        val specialAttack = stats[3].base_stat
        val specialDefense = stats[4].base_stat
        val speed = stats[5].base_stat

        binding.apply {
            imageViewBack.setOnClickListener {
                findNavController().navigateUp()
            }
            savePokemon.visibility = View.VISIBLE
            releasePokemon.visibility = View.GONE
            textViewDetailsNameNumber.text =
                getString(
                    R.string.details_name_number,
                    name,
                    pokemon.id
                )
            textViewWeight.text = getString(R.string.pokemon_weight, weight)
            textViewHeight.text = getString(R.string.pokemon_height, height)

            Utils.apply {
                abilitiesNumberChecking(pokemon.abilities, textViewPokemonAbilities)
                progressBarSetup(progressBarHp, hp)
                progressBarSetup(progressBarAttack, attack)
                progressBarSetup(progressBarDefense, defense)
                progressBarSetup(progressBarSpecialAttack, specialAttack)
                progressBarSetup(progressBarSpecialDefense, specialDefense)
                progressBarSetup(progressBarSpeed, speed)
                checkNumberOfTypes(
                    types,
                    textViewTypeOne,
                    textViewTypeTwo,
                    cardViewTypeOne,
                    cardViewTypeTwo,
                    requireActivity()
                )
            }

            val abilities: String = binding.textViewPokemonAbilities.text as String
            val typeOne = binding.textViewTypeOne.text.toString()
            var typeTwo = ""
            if (binding.cardViewTypeTwo.isVisible) {
                typeTwo = binding.textViewTypeTwo.text.toString()
            }

            lifecycleScope.launch {
                val bitmap = Utils.getBitmapFromUrl(pokemon.imageUrl!!, requireActivity())
                binding.imageViewDetailsImage.setImageBitmap(bitmap)
                Utils.calculateDominantColor(bitmap, binding.pokemonDetailParentView)

                val pokemon = SavedPokemon( name, bitmap, typeOne, typeTwo, weight, height,
                    abilities, hp, attack, defense, specialAttack, specialDefense, speed)

                binding.savePokemon.setOnClickListener { catchPokemon(pokemon, it) }
            }
        }
    }

    private fun catchPokemon(pokemon: SavedPokemon, view: View) {
        sharedViewModel.savePokemon(pokemon)
        findNavController().navigateUp()
        Snackbar.make(view, "You caught ${pokemon.name}!", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun releasePokemon(pokemon: SavedPokemon, view: View) {
        sharedViewModel.deletePokemon(pokemon)
        findNavController().navigateUp()
        Snackbar.make(view, "You released ${pokemon.name}!", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}