package ru.roguelike.model

import ru.roguelike.util.Constants
import kotlin.math.min

/**
 * Class that store information about hero
 */
class Hero(
    override var _coordinates: Coordinates,
    override val maxHp: Int = Constants.MAX_ENEMY_HP,
) : Character() {
    private var _exp: Int = 0

    override val exp: Int
        get() = _exp

    private var shield: Shield? = null

    /**
     * Begin to use the item
     */
    fun use(item: Item): Boolean {
        if (shield != null && item is Shield) {
            println("1")
            return false
        }
        hp = minOf(maxHp, hp + item.getHpChange())
        damage += item.getDamageChange()
        armor += item.getArmorChange()
        println("2")
        if (item is Shield) {
            println("3")
            shield = item
        }
        return true
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
        if (item is Shield) {
            shield = null
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

    override fun attackedBy(character: Character) {
        shield?.reduceArmor(min(armor, character.damage))
        super.attackedBy(character)
    }
}
