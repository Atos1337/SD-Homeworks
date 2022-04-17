package ru.roguelike.logic

import ru.roguelike.model.Character
import ru.roguelike.model.Coordinates
import ru.roguelike.model.MapModel
import ru.roguelike.view.Drawable

class MapLogic(
    private val character: Character,
    private val mapModel: MapModel,
    private val view: Drawable
) : Logic {

    private fun checkCoordinates(c: Coordinates): Boolean {
        return 0 <= c.x && c.x < mapModel.getX() && 0 <= c.y && c.y < mapModel.getY() && mapModel.isWalkable(c)
    }

    /**
     * process moving left, move character to the adjacent left cell
     */
    override fun moveLeft() {
        if (!checkCoordinates(character.coordinates.getLeft())) {
            return
        }
        character.moveLeft()
        view.draw()
    }

    /**
     * process moving right, move character to the adjacent Right cell
     **/
    override fun moveRight() {
        if (!checkCoordinates(character.coordinates.getRight())) {
            return
        }
        character.moveRight()
        view.draw()
    }

    /**
     * process moving up, move character to the adjacent Up cell
     **/
    override fun moveUp() {
        if (!checkCoordinates(character.coordinates.getUp())) {
            return
        }
        character.moveUp()
        view.draw()
    }

    /**
     * process moving down, move character to the adjacent Down cell
     **/
    override fun moveDown() {
        if (!checkCoordinates(character.coordinates.getDown())) {
            return
        }
        character.moveDown()
        view.draw()
    }

    /**
     * draw current map
     **/
    override fun draw() {
        view.draw()
    }
}
