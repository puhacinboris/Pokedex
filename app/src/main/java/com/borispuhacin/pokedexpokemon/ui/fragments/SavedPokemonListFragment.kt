package com.borispuhacin.pokedexpokemon.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemon
import com.borispuhacin.pokedexpokemon.databinding.FragmentSavedPokemonListBinding
import com.borispuhacin.pokedexpokemon.ui.adapters.SavedPokemonAdapter
import com.borispuhacin.pokedexpokemon.ui.viewModels.SharedViewModel

class SavedPokemonListFragment : Fragment(), SavedPokemonAdapter.OnSavedPokemonClickListener {
    private var _binding: FragmentSavedPokemonListBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: SavedPokemonAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedPokemonListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = SavedPokemonAdapter(this)

        binding.imageViewReleaseAll.setOnClickListener { deleteDialog() }

        binding.recyclerViewSavedPokemon.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }

        sharedViewModel.getAllSaved.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.textViewNoSavedPokemon.visibility = View.VISIBLE
                binding.imageViewReleaseAll.visibility = View.GONE
            } else {
                binding.textViewNoSavedPokemon.visibility = View.GONE
                binding.imageViewReleaseAll.visibility = View.VISIBLE
            }
            listAdapter.submitList(it)
        }

    }

    private fun deleteDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Release All Pokemon")
            setMessage("Are you sure you want to release them all?")
            setPositiveButton("YES") { _, _ -> sharedViewModel.deleteAllPokemon() }
            setNegativeButton("No") {_, _ ->}
            create()
            show()
        }
    }

    override fun onItemClick(savedPokemon: SavedPokemon) {
        val action = SavedPokemonListFragmentDirections.actionSavedPokemonListFragmentToPokemonDetailFragment(savedPokemon)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}