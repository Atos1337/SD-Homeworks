package ru.roguelike.model

class CyborgFactory : EnemyFactory {
    override fun create(x: Int, y: Int): Enemy {
        return Enemy.createRandomEnemy(x, y, EnemyStyle.CYBORG)
    }
}
