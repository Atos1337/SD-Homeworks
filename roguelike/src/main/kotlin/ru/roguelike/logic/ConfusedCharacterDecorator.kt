package ru.roguelike.logic

import ru.roguelike.model.Coordinates
import kotlin.random.Random

/**
 * Decorator for confusion
 */
class ConfusedCharacterDecorator(
    private val characterStrategy: CharacterStrategy,
    private var confusionTimer: Int = 10,
) : CharacterStrategy {
    override fun move(enemyCoord: Coordinates, heroCoord: Coordinates): Coordinates {
        if (confusionTimer <= 0) {
            return characterStrategy.move(enemyCoord, heroCoord)
        }
        confusionTimer -= 1

        val possible = arrayOf(
            Coordinates(enemyCoord.x - 1, enemyCoord.y),
            Coordinates(enemyCoord.x, enemyCoord.y - 1),
            Coordinates(enemyCoord.x, enemyCoord.y + 1),
            Coordinates(enemyCoord.x + 1, enemyCoord.y),
        )
        return possible[Random.nextInt(possible.size)]
    }

    override fun getStrategy(): CharacterStrategy {
        if (confusionTimer <= 0) {
            return characterStrategy.getStrategy()
        }
        return this
    }
}
