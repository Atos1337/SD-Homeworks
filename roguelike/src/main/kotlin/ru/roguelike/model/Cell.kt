package ru.roguelike.model

/**
 * Specify whether character can walk on this cell
 */
enum class CellType {
    WALKABLE,
    NON_WALKABLE
}

/**
 * Class that stores information about field cell
 */
@kotlinx.serialization.Serializable
data class Cell(
    val cellType: CellType,
    var item: Item? = null,
    var enemy: Enemy? = null,
)
