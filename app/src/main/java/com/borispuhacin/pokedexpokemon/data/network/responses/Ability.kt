package com.borispuhacin.pokedexpokemon.data.network.responses


import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability")
    val ability: AbilityX,
    @SerializedName("slot")
    val slot: Int
)