package ru.roguelike.model

/**
 * Interface for items
 */
interface Item {
    /**
     * Method returns hp of object
     */
    fun getHpChange(): Int = 0
    /**
     * Method returns damage of object
     */
    fun getDamageChange(): Int = 0
    /**
     * Method returns armour of object
     */
    fun getArmorChange(): Int = 0
    override fun toString(): String

    fun reduceArmor(armorLoss: Int) {
    }
}

/**
 * Interface for disposable items
 */
interface DisposableItem : Item

/**
 * Interface for non-disposable items
 */
interface NonDisposableItem : Item
