package ru.roguelike.model

class DragonFactory : EnemyFactory {
    override fun create(x: Int, y: Int): Enemy {
        return Enemy.createRandomEnemy(x, y, EnemyStyle.DRAGON)
    }
}
