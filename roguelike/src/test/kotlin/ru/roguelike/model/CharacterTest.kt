package ru.roguelike.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CharacterTest {
    @Test
    fun testLeft() {
        val c = Hero(Coordinates(0, 0))

        val old = c.coordinates.copy()
        val expected = Coordinates(old.x - 1, old.y)

        c.moveLeft()

        Assertions.assertEquals(expected, c.coordinates)
    }

    @Test
    fun testRight() {
        val c = Hero(Coordinates(0, 0))

        val old = c.coordinates.copy()
        val expected = Coordinates(old.x + 1, old.y)

        c.moveRight()

        Assertions.assertEquals(expected, c.coordinates)
    }

    @Test
    fun testUp() {
        val c = Hero(Coordinates(0, 0))

        val old = c.coordinates.copy()
        val expected = Coordinates(old.x, old.y - 1)

        c.moveUp()

        Assertions.assertEquals(expected, c.coordinates)
    }

    @Test
    fun testDown() {
        val c = Hero(Coordinates(0, 0))

        val old = c.coordinates.copy()
        val expected = Coordinates(old.x, old.y + 1)

        c.moveDown()

        Assertions.assertEquals(expected, c.coordinates)
    }
}
