package com.borispuhacin.pokedexpokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.borispuhacin.pokedexpokemon.data.network.responses.PokemonApiResult
import com.borispuhacin.pokedexpokemon.databinding.ItemPokemonBinding
import com.borispuhacin.pokedexpokemon.util.Utils

class PokemonListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<PokemonApiResult, PokemonListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) listener.onItemClick(item)
                }
            }
        }

        fun bind(pokemonApiResult: PokemonApiResult) {
            val url = pokemonApiResult.url
            val number = if (url.endsWith("/")) {
                url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
                url.takeLastWhile { it.isDigit() }
            }
            val imageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png"
            Utils.loadImageUrl(binding.imageViewPokemonImg, imageUrl)

            binding.textViewPokemonName.text =
                pokemonApiResult.name.replaceFirstChar { it.uppercase() }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(pokemonApiResult: PokemonApiResult)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PokemonApiResult>() {
        override fun areItemsTheSame(
            oldItem: PokemonApiResult,
            newItem: PokemonApiResult
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: PokemonApiResult,
            newItem: PokemonApiResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}