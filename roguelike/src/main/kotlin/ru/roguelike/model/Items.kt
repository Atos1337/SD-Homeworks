package ru.roguelike.model

import ru.roguelike.util.Constants
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Util class for items
 */
object Items {
    /**
     * Create random item
     */
    fun createRandomItem(): Item = when (Random.nextInt(3)) {
        0 -> Shield(Random.nextInt(1..Constants.MAX_ARMOR))
        1 -> Sword(Random.nextInt(1..Constants.MAX_ENEMY_DAMAGE))
        2 -> Apple(Random.nextInt(1..Constants.MAX_ENEMY_HP))
        else -> error("Unreachable")
    }
}

/**
 * Class for shield.
 */
data class Shield(private var armor: Int) : NonDisposableItem {
    override fun reduceArmor(armorLoss: Int) {
        armor -= armorLoss
    }

    override fun getArmorChange(): Int = armor
    /**
     * Method returns armour of object
     */
    override fun toString(): String {
        return "SHIELD, ARMOUR +" + getArmorChange().toString()
    }
}

/**
 * Class for sword.
 */
data class Sword(private val damage: Int) : NonDisposableItem {
    /**
     * Method returns damage of object
     */
    override fun getDamageChange(): Int = damage
    override fun toString(): String {
        return "SWORD, DAMAGE +" + getDamageChange().toString()
    }
}

/**
 * Class for apple.
 */
data class Apple(private val hp: Int) : DisposableItem {
    /**
     * Method returns hp of object
     */
    override fun getHpChange(): Int = hp
    override fun toString(): String {
        return "APPLE, HP +" + getHpChange().toString()
    }
}
