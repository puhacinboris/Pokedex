package com.borispuhacin.pokedexpokemon.data.database

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saved_pokemon")
@Parcelize
data class SavedPokemon(
    val name: String,
    val image: Bitmap,
    val typeOne: String,
    val typeTwo: String? = null,
    val weight: String,
    val height: String,
    val abilities: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {

}
