package ru.roguelike.logic

import ru.roguelike.model.Coordinates
import kotlin.math.sign

/**
 * Class that stores information about sneaky strategy of enemy
 */
class SneakyStrategy : CharacterStrategy {
    /**
     * Method that moves enemy
     */
    override fun move(enemyCoord: Coordinates, heroCoord: Coordinates): Coordinates {
        val vec = Coordinates(
            sign((heroCoord.x - enemyCoord.x).toDouble()).toInt(),
            sign((heroCoord.y - enemyCoord.y).toDouble()).toInt()
        )

        val dxdys = listOf(
            Coordinates(vec.x, 0),
            Coordinates(0, vec.y)
        ).filter { it != Coordinates(0, 0) }

        if (dxdys.isEmpty()) {
            return enemyCoord
        }

        val dxdy = dxdys.random()
        return Coordinates(enemyCoord.x - dxdy.x, enemyCoord.y - dxdy.y)
    }
}
