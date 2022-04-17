package ru.roguelike.model

/**
 * Class that stores information about main character
 */
class Character(coordinates: Coordinates) {
    var coordinates: Coordinates = coordinates
        private set
    var damage: Int = 5
        private set
    var hp: Int = 2
        private set
    val maxHp: Int = 5
    var armor: Int = 0
        private set

    /**
     * Move character to one cell right
     */
    fun moveRight() {
        coordinates = coordinates.getRight()
    }

    /**
     * Move character to one cell left
     */
    fun moveLeft() {
        coordinates = coordinates.getLeft()
    }

    /**
     * Move character to one cell up
     */
    fun moveUp() {
        coordinates = coordinates.getUp()
    }

    /**
     * Move character to one cell down
     */
    fun moveDown() {
        coordinates = coordinates.getDown()
    }

    fun use(item: Item) {
        hp = minOf(maxHp, hp + item.getHpChange())
        damage += item.getDamageChange()
        armor += item.getArmorChange()
    }

    fun unUse(item: NonDisposableItem) {
        hp = maxOf(1, hp - item.getHpChange())
        damage -= item.getDamageChange()
        armor -= item.getArmorChange()
    }
}
