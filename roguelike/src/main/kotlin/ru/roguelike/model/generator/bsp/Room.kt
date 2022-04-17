package ru.roguelike.model.generator.bsp

import ru.roguelike.model.Coordinates
import kotlin.random.Random

/**
 * Class that represents dungeon room
 */
internal data class Room(val leftBottom: Coordinates, val width: Int, val height: Int) {
    private val left: Int get() = leftBottom.x
    private val right: Int get() = leftBottom.x + width
    private val bottom: Int get() = leftBottom.y
    private val top: Int get() = leftBottom.y + height

    /**
     * Get random point from room except borders
     */
    val randomPoint: Coordinates get() = Coordinates(
        Random.nextInt(left + 1, right - 1),
        Random.nextInt(bottom + 1, top - 1)
    )
}
