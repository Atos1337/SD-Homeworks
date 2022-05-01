package ru.roguelike.model

import ru.roguelike.logic.AgressiveStrategy
import ru.roguelike.logic.CharacterStrategy
import ru.roguelike.logic.ConfusedCharacterDecorator
import ru.roguelike.logic.MapLogic
import ru.roguelike.logic.PassiveStrategy
import ru.roguelike.logic.SneakyStrategy
import ru.roguelike.util.Constants
import ru.roguelike.view.AGRESSIVE_CHAR
import ru.roguelike.view.CONFUSED_CHAR
import ru.roguelike.view.PASSIVE_CHAR
import ru.roguelike.view.SNEAKY_CHAR
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Class that store information about enemy
 */
@kotlinx.serialization.Serializable
class Enemy(
    override var _coordinates: Coordinates,
    override val maxHp: Int,
    private var strategy: CharacterStrategy,
) : Character() {
    /**
     * WantedMove
     */
    fun wantedMove(mapLogic: MapLogic, heroCoordinates: Coordinates): Coordinates {
        if (!mapLogic.isHeroVisible(coordinates)) {
            return coordinates
        }
        strategy = strategy.getStrategy()
        val newCoordinates = strategy.move(coordinates, heroCoordinates)
        if (mapLogic.checkCoordinates(newCoordinates)) {
            return newCoordinates
        }
        return coordinates
    }

    /**
     * Move
     */
    fun move(coordinates: Coordinates) {
        _coordinates = coordinates
    }

    /**
     * Self-confusion with the specified probability
     */
    fun confuse() {
        if (Random.nextDouble() <= Constants.CONFUSE_PROBABILITY) {
            strategy = ConfusedCharacterDecorator(strategy)
        }
    }

    /**
     * String representation of the enemy
     */
    override fun toString(): String {
        if (strategy.getStrategy() is AgressiveStrategy) {
            return AGRESSIVE_CHAR.toString()
        }
        if (strategy.getStrategy() is SneakyStrategy) {
            return SNEAKY_CHAR.toString()
        }
        if (strategy.getStrategy() is PassiveStrategy) {
            return PASSIVE_CHAR.toString()
        }
        if (strategy.getStrategy() is ConfusedCharacterDecorator) {
            return CONFUSED_CHAR.toString()
        }
        throw Exception("")
    }

    companion object {
        private val strategies = listOf(AgressiveStrategy(), SneakyStrategy(), PassiveStrategy())

        fun createRandomEnemy(x: Int, y: Int): Enemy {
            val hp = Random.nextInt(1..Constants.MAX_HP)
            val enemy = Enemy(Coordinates(x, y), hp, strategies.random())
            enemy.hp = hp
            enemy.damage = Random.nextInt(1..Constants.MAX_DAMAGE)

            return enemy
        }
    }

    override val exp: Int
        get() = maxHp + damage
}
