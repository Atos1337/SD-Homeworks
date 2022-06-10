package ru.roguelike.model

import ru.roguelike.util.Constants
import kotlin.math.min

/**
 * Class that stores information about characters
 */
@kotlinx.serialization.Serializable
abstract class Character {
    protected abstract var _coordinates: Coordinates
    val coordinates get() = _coordinates

    var damage: Int = Constants.MAX_ENEMY_DAMAGE
        protected set
    var hp: Int = Constants.MAX_ENEMY_HP
        protected set
    abstract val maxHp: Int
    var armor: Int = 0
        protected set
    abstract val exp: Int
    protected var armorLoss: Int = 0

    /**
     * Move character to one cell right
     */
    fun moveRight() {
        _coordinates = coordinates.getRight()
    }

    /**
     * Move character to one cell left
     */
    fun moveLeft() {
        _coordinates = coordinates.getLeft()
    }

    /**
     * Move character to one cell up
     */
    fun moveUp() {
        _coordinates = coordinates.getUp()
    }

    /**
     * Move character to one cell down
     */
    fun moveDown() {
        _coordinates = coordinates.getDown()
    }

    /**
     * Attack another character
     */
    open fun attackedBy(character: Character) {
        val damageToArmor = min(armor, character.damage)
        armorLoss += damageToArmor
        armor -= damageToArmor
        hp -= character.damage - damageToArmor
    }

    companion object {
        /**
         * The duel act for hero and enemy
         */
        fun duel(hero: Hero, enemy: Enemy) {
            hero.attackedBy(enemy)
            enemy.attackedBy(hero)
            enemy.confuse()
            if (enemy.isDead()) {
                hero.tryLevelUp(enemy.exp)
            }
        }
    }

    /**
     * Check whether we dead or not
     */
    fun isDead(): Boolean = hp <= 0
}
