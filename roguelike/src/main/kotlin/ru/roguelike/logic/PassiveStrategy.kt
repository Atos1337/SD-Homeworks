package ru.roguelike.logic

import ru.roguelike.model.Coordinates

/**
 * Class that stores information about passive strategy of enemy
 */
class PassiveStrategy : CharacterStrategy {
    /**
     * Method that moves enemy
     */
    override fun move(enemyCoord: Coordinates, heroCoord: Coordinates): Coordinates {
        return enemyCoord
    }
}
