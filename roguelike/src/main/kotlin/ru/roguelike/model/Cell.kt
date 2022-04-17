package ru.roguelike.model

/**
 * Specify wwhether character can walk on this cell
 */
enum class CellType {
    WALKABLE,
    NON_WALKABLE
}

/**
 * Class that stores information about field cell
 */
@kotlinx.serialization.Serializable
data class Cell(val cellType: CellType)
