package ru.roguelike.logic

import ru.roguelike.model.Coordinates

/**
 * Strategy that specifies algorithm according to which our enemy moves
 */
interface CharacterStrategy {
    /**
     * Calculates the coordinates for the next step
     */
    fun move(enemyCoord: Coordinates, heroCoord: Coordinates): Coordinates

    /**
     * Returns the strategy
     */
    fun getStrategy(): CharacterStrategy {
        return this
    }
}
