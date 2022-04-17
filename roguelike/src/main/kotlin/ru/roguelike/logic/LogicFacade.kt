package ru.roguelike.logic

class LogicFacade(
    private val map: MapLogic,
    private val instructions: InstructionsLogic,
    private var current: Logic = map,
) {

    /**
     * process moving left
     */
    fun moveLeft() = current.moveLeft()

    /**
     * process moving right
     */
    fun moveRight() = current.moveRight()

    /**
     * process moving up
     */
    fun moveUp() = current.moveUp()

    /**
     * process moving down
     */
    fun moveDown() = current.moveDown()

    /**
     * change current logic onto instructions and draw it
     */
    fun processHelp() {
        current = instructions
        current.draw()
    }

    /**
     * change current logic onto map and draw it
     */
    fun processMap() {
        current = map
        current.draw()
    }
}
