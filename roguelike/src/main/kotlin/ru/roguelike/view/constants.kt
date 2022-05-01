package ru.roguelike.view

// INSTRUCTIONS
val AUTHORS = listOf("Kirill", "Andrey", "Misha", "Vitya")
val INSTRUCTIONS = mapOf(
    Pair("H", "Help"),
    Pair("M", "Map"),
    Pair("I", "Inventory"),
    Pair("D", "Drop item"),
    Pair("E", "Equip item"),
    Pair("U", "Unequip item ")
)

// MAP
const val WALKABLE_CHAR = '.'
const val NON_WALKABLE_CHAR = '#'

// CHARACTER
const val CHARACTER_CHAR = '*'

// INVENTORY
const val SWORD_CHAR = '$'
const val SHIELD_CHAR = '-'
const val APPLE_CHAR = '@'

// ENEMY
const val AGRESSIVE_CHAR = 'A'
const val PASSIVE_CHAR = 'P'
const val SNEAKY_CHAR = 'S'
const val CONFUSED_CHAR = 'C'

const val DESCRIPTION =
    "Main character  " + CHARACTER_CHAR +
        ",Can walk        " + WALKABLE_CHAR +
        ",Can't walk      " + NON_WALKABLE_CHAR +
        ",Sword           " + SWORD_CHAR +
        ",Shield          " + SHIELD_CHAR +
        ",Apple           " + APPLE_CHAR +
        ",Agressive enemy " + AGRESSIVE_CHAR +
        ",Passive enemy   " + PASSIVE_CHAR +
        ",Sneaky enemy    " + SNEAKY_CHAR
