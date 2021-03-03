package com.orange.pokemon.data

import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity(tableName = "pokemon_table")

data class PokemonEntity (

    @PrimaryKey(autoGenerate = true)

    var id: Int = 0,

    //val abilities: List<String>,
    val attack: Int=0,

    val baseExp: String,
    val category: String,
    val cycles: String,
    val imageurl: String,
    val name: String,

    val defense: Int=0,

    val eggGroups: String="",
    //var evolutions: List<String>? = null,
    val evolvedfrom: String="",

    val femalePercentage: String="",
    val genderless: Int=0,
    val height: String="",
    val hp: Int=0,


    val malePercentage: String="",
    val reason: String="",

    val specialAttack: Int=0,

    val specialDefense: Int=0,
    val speed: Int=0,
    val total: Int=0,
    //val typeofpokemon: List<String>,
    //val weaknesses: List<String>,
    val weight: String="",
    val xdescription: String="",
    val ydescription: String=""



)
