package ru.roguelike.model

import ru.roguelike.util.Constants

/**
 * Class that store information about hero
 */
class Hero(
    override var _coordinates: Coordinates,
    override val maxHp: Int = Constants.MAX_HP,
) : Character() {
    private var _exp: Int = 0

    override val exp: Int
        get() = _exp
    /**
     * Begin to use the item
     */
    fun use(item: Item) {
        hp = minOf(maxHp, hp + item.getHpChange())
        damage += item.getDamageChange()
        armor += item.getArmorChange()
    }

    /**
     * End to use the item
     */
    fun unUse(item: NonDisposableItem) {
        hp = maxOf(1, hp - item.getHpChange())
        damage -= item.getDamageChange()

        val armorChange = item.getArmorChange()

        if (armorChange >= armorLoss) {
            armor -= armorChange - armorLoss
            item.reduceArmor(armorLoss)
            armorLoss = 0
        } else {
            item.reduceArmor(armorChange)
            armorLoss -= armorChange
        }
    }

    /**
     * try level up our hero
     */
    fun tryLevelUp(addedExp: Int) {
        _exp += addedExp
        if (exp >= Constants.EXP_FOR_LEVEL_UP) {
            val levels = exp / Constants.EXP_FOR_LEVEL_UP
            hp += levels * Constants.HP_INCREASE_FOR_LEVEL
            damage += levels * Constants.DAMAGE_INCREASE_FOR_LEVEL
            _exp = exp % Constants.EXP_FOR_LEVEL_UP
        }
    }
}
