package ru.roguelike.model

import ru.roguelike.logic.AgressiveStrategy
import ru.roguelike.logic.CharacterStrategy
import ru.roguelike.logic.ConfusedCharacterDecorator
import ru.roguelike.logic.MapLogic
import ru.roguelike.logic.PassiveStrategy
import ru.roguelike.logic.SneakyStrategy
import ru.roguelike.util.Constants
import ru.roguelike.util.Constants.CLONABLE_ENEMIES
import ru.roguelike.util.Constants.CYBORG_REGEN
import ru.roguelike.util.Constants.DRAGON_REGEN
import ru.roguelike.util.Constants.PANIC_HP_BOUND
import ru.roguelike.util.Constants.SKELETON_REGEN
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
open class Enemy(
    override var _coordinates: Coordinates,
    override val maxHp: Int,
    protected var initStrategy: CharacterStrategy,
    val style: EnemyStyle
) : Character() {
    var currentStrategy: CharacterStrategy = initStrategy

    /**
     * WantedMove
     */
    fun wantedMove(mapLogic: MapLogic, heroCoordinates: Coordinates): Coordinates {
        if (!mapLogic.isHeroVisible(coordinates)) {
            return coordinates
        }
        currentStrategy = currentStrategy.getStrategy()
        val newCoordinates = currentStrategy.move(coordinates, heroCoordinates)
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
        val p = hp * 1.0 / maxHp
        currentStrategy = if (0 <= p && p < PANIC_HP_BOUND) {
            SneakyStrategy()
        } else {
            initStrategy
        }
        val regenHP = when (style) {
            EnemyStyle.DRAGON -> DRAGON_REGEN
            EnemyStyle.CYBORG -> CYBORG_REGEN
            EnemyStyle.SKELETON -> SKELETON_REGEN
        }
        hp = maxOf(maxHp, hp + regenHP)
    }

    /**
     * Self-confusion with the specified probability
     */
    fun confuse() {
        if (Random.nextDouble() <= Constants.CONFUSE_PROBABILITY) {
            currentStrategy = ConfusedCharacterDecorator(currentStrategy)
        }
    }

    /**
     * String representation of the enemy
     */
    override fun toString(): String {
        if (currentStrategy.getStrategy() is AgressiveStrategy) {
            return AGRESSIVE_CHAR.toString()
        }
        if (currentStrategy.getStrategy() is SneakyStrategy) {
            return SNEAKY_CHAR.toString()
        }
        if (currentStrategy.getStrategy() is PassiveStrategy) {
            return PASSIVE_CHAR.toString()
        }
        if (currentStrategy.getStrategy() is ConfusedCharacterDecorator) {
            return CONFUSED_CHAR.toString()
        }
        throw Exception("")
    }

    companion object {
        private val strategies = listOf(AgressiveStrategy(), SneakyStrategy(), PassiveStrategy())

        fun createRandomEnemy(x: Int, y: Int, style: EnemyStyle): Enemy {
            val hp = Random.nextInt(1..Constants.MAX_ENEMY_HP)
            val enemy = if (style in CLONABLE_ENEMIES) {
                CloneableEnemy(Coordinates(x, y), hp, strategies.random(), style)
            } else {
                Enemy(Coordinates(x, y), hp, strategies.random(), style)
            }
            enemy.hp = hp
            enemy.damage = Random.nextInt(1..Constants.MAX_ENEMY_DAMAGE)

            return enemy
        }
    }

    override val exp: Int
        get() = maxHp + damage

    /**
     * Method for clone our enemy
     */
    open fun clone(coordinates: Coordinates): Enemy? {
        return null
    }
}
