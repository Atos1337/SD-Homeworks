package ru.roguelike.model

import ru.roguelike.logic.CharacterStrategy

/**
 * Enemy that can be cloned
 */
class CloneableEnemy(
    _coordinates: Coordinates,
    maxHp: Int,
    strategy: CharacterStrategy,
    style: EnemyStyle
) : Enemy(_coordinates, maxHp, strategy, style) {
    override fun clone(coordinates: Coordinates): Enemy {
        val enemy = CloneableEnemy(coordinates, maxHp, initStrategy, style)
        enemy.hp = hp
        enemy.damage = damage

        return enemy
    }
}
