package com.borispuhacin.pokedexpokemon.util

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.borispuhacin.pokedexpokemon.R
import com.borispuhacin.pokedexpokemon.data.network.responses.Ability
import com.borispuhacin.pokedexpokemon.data.network.responses.Type
import com.google.android.material.card.MaterialCardView

object Utils {

    suspend fun getBitmapFromUrl(imageUrl: String, context: Context): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .build()
        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    fun loadImageUrl(imageView: ImageView, url: String) {
        val imageUrl = url.toUri().buildUpon().scheme("https").build()
        imageView.load(imageUrl) {
            crossfade(true)
            placeholder(R.drawable.pikachu_loading)
            error(R.drawable.ic_broken_image)
        }
    }

    fun calculateDominantColor(drawable: Bitmap, view: View){
        val bitmap = drawable.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bitmap).generate { palette ->
            palette?.apply {
                dominantSwatch?.let {
                    val gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        intArrayOf(
                            it.rgb,
                            Color.WHITE
                        ))
                    view.background = gradient
                }
            }
        }
    }

    fun abilitiesNumberChecking(abilities: List<Ability>, textView: TextView) {
        val abilitiesList = mutableListOf<String>()

        abilities.forEach { ability ->
            abilitiesList.add(ability.ability.name.replaceFirstChar { it.uppercase() })
        }

        if (abilitiesList.size > 1) {
            textView.text = abilitiesList.joinToString(", ")
        } else textView.text = abilitiesList.joinToString()
    }

    fun progressBarSetup(progressBar: ProgressBar, stat: Int) {
        ObjectAnimator.ofInt(progressBar,"progress", stat)
            .setDuration(1500)
            .start()
    }

    fun checkNumberOfTypesSavedPokemon(
        types: List<String>,
        textViewOne: TextView,
        textViewTwo: TextView,
        cardViewOne: MaterialCardView,
        cardViewTwo: MaterialCardView,
        context: Context
    ) {
        val typeOne = types[0]
        textViewOne.text = typeOne
        typeTextColor(typeOne, textViewOne)
        typeBackgroundColor(typeOne, cardViewOne, context)

        if (types.size > 1) {
            cardViewTwo.visibility = View.VISIBLE
            val typeTwo = types[1]
            textViewTwo.text = typeTwo
            typeTextColor(typeTwo, textViewTwo)
            typeBackgroundColor(typeTwo, cardViewTwo, context)
        } else {
            cardViewTwo.visibility = View.GONE
        }
    }

    fun checkNumberOfTypes(
        types: List<Type>,
        textViewOne: TextView,
        textViewTwo: TextView,
        cardViewOne: MaterialCardView,
        cardViewTwo: MaterialCardView,
        context: Context
    ) {
        val typeOne = types[0].type.name.replaceFirstChar { it.uppercase() }
        textViewOne.text = typeOne
        typeTextColor(typeOne, textViewOne)
        typeBackgroundColor(typeOne, cardViewOne, context)

        if (types.size > 1) {
            cardViewTwo.visibility = View.VISIBLE
            val typeTwo = types[1].type.name.replaceFirstChar { it.uppercase() }
            textViewTwo.text = typeTwo
            typeTextColor(typeTwo, textViewTwo)
            typeBackgroundColor(typeTwo, cardViewTwo, context)
        } else {
            cardViewTwo.visibility = View.GONE
        }
    }

    private fun typeTextColor(type: String, textView: TextView) {
        when (type) {
            "Electric" -> textView.setTextColor(Color.BLACK)
            "Bug" -> textView.setTextColor(Color.BLACK)
            "Grass" -> textView.setTextColor(Color.BLACK)
            "Psychic" -> textView.setTextColor(Color.BLACK)
            "Fairy" -> textView.setTextColor(Color.BLACK)
            "Steal" -> textView.setTextColor(Color.BLACK)
            "Ice" -> textView.setTextColor(Color.BLACK)
            "Normal" -> textView.setTextColor(Color.BLACK)
            else -> {
                textView.setTextColor(Color.WHITE)
            }
        }
    }

    private fun typeBackgroundColor(type: String, materialCardView: MaterialCardView, context: Context) {
        when (type) {
            "Ground" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_ground)
            )
            "Rock" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_rock)
            )
            "Electric" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_electric)
            )
            "Bug" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_bug)
            )
            "Grass" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_grass)
            )
            "Dark" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_dark)
            )
            "Ghost" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_ghost)
            )
            "Poison" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_poison)
            )
            "Psychic" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_psychic)
            )
            "Fairy" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_fairy)
            )
            "Fighting" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_fighting)
            )
            "Fire" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_fire)
            )
            "Dragon" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_dragon)
            )
            "Flying" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_flying)
            )
            "Steel" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_steel)
            )
            "Normal" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_normal)
            )
            "Water" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_water)
            )
            "Ice" -> materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.type_ice)
            )
        }
    }

    fun Int.hexString():String{
        return String.format("#%06X", (0xFFFFFF and  this))
    }
}