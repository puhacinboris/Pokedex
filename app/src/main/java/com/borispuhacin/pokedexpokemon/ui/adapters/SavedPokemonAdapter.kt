package com.borispuhacin.pokedexpokemon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.borispuhacin.pokedexpokemon.data.database.SavedPokemon
import com.borispuhacin.pokedexpokemon.databinding.ItemPokemonBinding

class SavedPokemonAdapter(private val listener: OnSavedPokemonClickListener) :
    ListAdapter<SavedPokemon, SavedPokemonAdapter.ViewHolder>(DiffCallbackSavedPokemon) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
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

        fun bind(pokemon: SavedPokemon) {
            binding.imageViewPokemonImg.load(pokemon.image)
            binding.textViewPokemonName.text = pokemon.name
        }
    }

    interface OnSavedPokemonClickListener {
        fun onItemClick(savedPokemon: SavedPokemon)
    }

    companion object DiffCallbackSavedPokemon : DiffUtil.ItemCallback<SavedPokemon>() {
        override fun areItemsTheSame(oldItem: SavedPokemon, newItem: SavedPokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SavedPokemon, newItem: SavedPokemon): Boolean {
            return oldItem == newItem
        }
    }
}