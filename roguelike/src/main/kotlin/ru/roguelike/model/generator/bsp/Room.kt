package ru.roguelike.model.generator.bsp

import ru.roguelike.model.Coordinates
import ru.roguelike.model.Enemy
import ru.roguelike.model.EnemyFactory
import ru.roguelike.model.Item
import ru.roguelike.model.Items
import ru.roguelike.util.Constants
import kotlin.random.Random

/**
 * Class that represents dungeon room
 */
internal data class Room(
    val leftBottom: Coordinates,
    val width: Int,
    val height: Int,
    val enemies: List<Enemy> = listOf(),
    val items: List<Pair<Item, Coordinates>> = listOf(),
) {
    private val left: Int get() = leftBottom.x
    private val right: Int get() = leftBottom.x + width
    private val bottom: Int get() = leftBottom.y
    private val top: Int get() = leftBottom.y + height

    /**
     * Get random point from room except borders
     */
    val randomPoint: Coordinates get() = Coordinates(
        Random.nextInt(left + 1, right - 1),
        Random.nextInt(bottom + 1, top - 1)
    )

    companion object {
        fun createRandomRoom(width: Int, height: Int, x: Int, y: Int, enemyFactory: EnemyFactory): Room {
            val roomWidth = Random.nextInt(Constants.ROOM_SIZE, width - 1)
            val roomHeight = Random.nextInt(Constants.ROOM_SIZE, height - 1)
            val roomCoordinates = Coordinates(
                Random.nextInt(1, width - roomWidth),
                Random.nextInt(1, height - roomHeight)
            )

            val enemies = mutableListOf<Enemy>()
            val items = mutableListOf<Pair<Item, Coordinates>>()
            val busyCells = setOf<Coordinates>()
            (1..Constants.ROOM_ENEMY_COUNT).forEach { _ ->
                var coordinates = Coordinates.createRandomCoordinates(roomCoordinates.x, roomCoordinates.y, roomWidth, roomHeight)
                while (busyCells.contains(coordinates)) {
                    coordinates = Coordinates.createRandomCoordinates(roomCoordinates.x, roomCoordinates.y, roomWidth, roomHeight)
                }
                coordinates += Coordinates(x, y)
                enemies.add(enemyFactory.create(coordinates.x, coordinates.y))
            }

            (1..Constants.ROOM_ITEMS_COUNT).forEach { _ ->
                var coordinates = Coordinates.createRandomCoordinates(roomCoordinates.x, roomCoordinates.y, roomWidth, roomHeight)
                while (busyCells.contains(coordinates)) {
                    coordinates = Coordinates.createRandomCoordinates(roomCoordinates.x, roomCoordinates.y, roomWidth, roomHeight)
                }
                coordinates += Coordinates(x, y)
                items.add(Items.createRandomItem() to coordinates)
            }

            return Room(Coordinates(x, y) + roomCoordinates, roomWidth, roomHeight, enemies, items)
        }
    }
}
