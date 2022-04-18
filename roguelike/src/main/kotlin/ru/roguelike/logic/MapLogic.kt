package ru.roguelike.logic

import ru.roguelike.model.Character
import ru.roguelike.model.Coordinates
import ru.roguelike.model.Enemy
import ru.roguelike.model.Hero
import ru.roguelike.model.InventoryModel
import ru.roguelike.model.MapModel
import ru.roguelike.view.Drawable
import kotlin.math.max
import kotlin.math.min

/**
 * Class that stores logic about map
 */
class MapLogic(
    private val hero: Hero,
    private val mapModel: MapModel,
    private val inventoryModel: InventoryModel,
    private val view: Drawable
) : Logic {
    /**
     * Method that checks whether hero can move to the coordinates
     */
    fun checkCoordinates(c: Coordinates): Boolean {
        return checkTransparent(c) && mapModel.isWithoutEnemy(c)
    }

    /**
     * Method that checks whether character can see through cell or not
     */
    private fun checkTransparent(c: Coordinates): Boolean {
        return 0 <= c.x &&
                c.x < mapModel.getX() &&
                0 <= c.y &&
                c.y < mapModel.getY() &&
                mapModel.isWalkable(c)
    }

    /**
     * Method that checks whether the point on map is visible
     */
    private fun isVisible(c1: Coordinates, c2: Coordinates): Boolean {
        if (c1 == c2) {
            return true
        }

        if (c1.x == c2.x) {
            for (y in min(c1.y, c2.y)..max(c1.y, c2.y)) {
                if (!checkTransparent(Coordinates(c1.x, y))) {
                    return false
                }
            }
            return true
        }

        if (c1.y == c2.y) {
            for (x in min(c1.x, c2.x)..max(c1.x, c2.x)) {
                if (!checkTransparent(Coordinates(x, c1.y))) {
                    return false
                }
            }
            return true
        }

        // c1.x != c2.x && c1.y != c2.y
        var cc1: Coordinates
        var cc2: Coordinates

        if (c1.x < c2.x) {
            cc1 = c1; cc2 = c2
        } else {
            cc1 = c2; cc2 = c1
        }
        for (x in cc1.x + 1..cc2.x) {
            val y = (x - cc1.x - 0.5) * (cc2.y - cc1.y) * 1.0 / (cc2.x - cc1.x) + cc1.y + 0.5
            if (!checkTransparent(Coordinates(x - 1, y.toInt()))) {
                return false
            }
            if (!checkTransparent(Coordinates(x, y.toInt()))) {
                return false
            }
            if (y.toInt().toDouble() == y) {
                if (!checkTransparent(Coordinates(x - 1, y.toInt() - 1))) {
                    return false
                }
                if (!checkTransparent(Coordinates(x, y.toInt() - 1))) {
                    return false
                }
            }
        }

        if (c1.y < c2.y) {
            cc1 = c1; cc2 = c2
        } else {
            cc1 = c2; cc2 = c1
        }
        for (y in cc1.y + 1..cc2.y) {
            val x = (y - cc1.y - 0.5) * (cc2.x - cc1.x) * 1.0 / (cc2.y - cc1.y) + cc1.x + 0.5
            if (!checkTransparent(Coordinates(x.toInt(), y - 1))) {
                return false
            }
            if (!checkTransparent(Coordinates(x.toInt(), y))) {
                return false
            }
            if (x.toInt().toDouble() == x) {
                if (!checkTransparent(Coordinates(x.toInt() - 1, y - 1))) {
                    return false
                }
                if (!checkTransparent(Coordinates(x.toInt() - 1, y))) {
                    return false
                }
            }
        }

        return true
    }

    /**
     * Method that checks whether the hero is visible
     */
    fun isHeroVisible(c: Coordinates) = isVisible(hero.coordinates, c)

    /**
     * Method that updates NPCs positions and statuses
     */
    private fun updateNPCs() {
        val enemies = mutableListOf<Enemy>()

        for (row in mapModel.field) {
            for (cell in row) {
                cell.enemy?.let {
                    enemies.add(it)
                }
            }
        }

        for (enemy in enemies) {
            val oldCoordinates = enemy.coordinates
            val newCoordinates = enemy.move(this, hero.coordinates)

            if (newCoordinates == hero.coordinates) {
                tryAttack(enemy.coordinates)
                if (hero.isDead()) {
                    println("YOU DEAD")
                }
            } else if (oldCoordinates != newCoordinates) {
                mapModel.field[oldCoordinates.y][oldCoordinates.x].enemy = null
                mapModel.field[newCoordinates.y][newCoordinates.x].enemy = enemy
            }
        }
    }

    private fun tryAttack(coordinates: Coordinates) {
        val enemy = mapModel.field[coordinates.y][coordinates.x].enemy
        enemy?.let {
            Character.duel(hero, it)
            if (it.isDead()) {
                mapModel.field[coordinates.y][coordinates.x].enemy = null
            }
        }
    }

    /**
     * process moving left, move character to the adjacent left cell
     */
    override fun moveLeft() {
        tryAttack(hero.coordinates.getLeft())
        if (checkCoordinates(hero.coordinates.getLeft())) {
            hero.moveLeft()
        }
        updateNPCs()
    }

    /**
     * process moving right, move character to the adjacent Right cell
     **/
    override fun moveRight() {
        tryAttack(hero.coordinates.getRight())
        if (checkCoordinates(hero.coordinates.getRight())) {
            hero.moveRight()
        }
        updateNPCs()
    }

    /**
     * process moving up, move character to the adjacent Up cell
     **/
    override fun moveUp() {
        tryAttack(hero.coordinates.getUp())
        if (checkCoordinates(hero.coordinates.getUp())) {
            hero.moveUp()
        }
        updateNPCs()
    }

    /**
     * process moving down, move character to the adjacent Down cell
     **/
    override fun moveDown() {
        tryAttack(hero.coordinates.getDown())
        if (checkCoordinates(hero.coordinates.getDown())) {
            hero.moveDown()
        }
        updateNPCs()
    }

    override fun processEquip() {
        mapModel.field[hero.coordinates.y][hero.coordinates.x].item?.let {
            inventoryModel.addItem(it)
        } ?: view.setError("There is no item")
        mapModel.field[hero.coordinates.y][hero.coordinates.x].item = null
    }

    /**
     * draw current map
     **/
    override fun draw() {
        view.draw()
    }
}
