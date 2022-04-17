package ru.roguelike.model

/**
 * Class that store coordinates and support operations with them
 */
data class Coordinates(private var _x: Int, private var _y: Int) {
    val x get() = _x
    val y get() = _y
    fun getLeft() = Coordinates(x - 1, y)
    fun getRight() = Coordinates(x + 1, y)
    fun getUp() = Coordinates(x, y - 1)
    fun getDown() = Coordinates(x, y + 1)

    operator fun plus(other: Coordinates): Coordinates {
        return Coordinates(x + other.x, y + other.y)
    }

    fun swapX(other: Coordinates) {
        _x = other._x.also { other._x = _x }
    }

    fun swapY(other: Coordinates) {
        _y = other._y.also { other._y = _y }
    }
}
