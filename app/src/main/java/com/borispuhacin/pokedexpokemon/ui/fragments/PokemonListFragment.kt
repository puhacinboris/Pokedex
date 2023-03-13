package com.borispuhacin.pokedexpokemon.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.borispuhacin.pokedexpokemon.R
import com.borispuhacin.pokedexpokemon.data.network.responses.PokemonApiResult
import com.borispuhacin.pokedexpokemon.databinding.FragmentPokemonListBinding
import com.borispuhacin.pokedexpokemon.ui.adapters.PokemonListAdapter
import com.borispuhacin.pokedexpokemon.ui.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PokemonListFragment : Fragment(), PokemonListAdapter.OnItemClickListener {
    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: PokemonListAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = PokemonListAdapter(this)

        binding.recyclerView.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }

        sharedViewModel.pokemonList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)

        }

        adapterApiList()
        searchView()
    }

    private fun adapterApiList() {
        sharedViewModel.pokemonList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun searchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val searchList = mutableListOf<PokemonApiResult>()
                if (!query.isNullOrEmpty()) {
                    sharedViewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
                        pokemonList.forEach { entry ->
                            if (entry.name.lowercase(Locale.ROOT).contains(query)) {
                                searchList.add(entry)
                            }
                        }
                    }
                    listAdapter.submitList(searchList)
                } else {
                    adapterApiList()
                }
                return false
            }
        })
    }

    override fun onItemClick(pokemonApiResult: PokemonApiResult) {
        sharedViewModel.getPokemon(pokemonApiResult.name)
        findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}