package ru.roguelike.model

/**
 * Interface for creating enemies
 * */
interface EnemyFactory {
    fun create(x: Int, y: Int): Enemy
}
