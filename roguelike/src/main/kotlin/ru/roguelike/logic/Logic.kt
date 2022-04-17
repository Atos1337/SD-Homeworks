package ru.roguelike.logic

interface Logic {
    /**
     * process moving left
     */
    fun moveLeft()

    /**
     * process moving right
     **/
    fun moveRight()

    /**
     * process moving up
     **/
    fun moveUp()

    /**
     * process moving down
     **/
    fun moveDown()

    /**
     * draw current logic
     **/
    fun draw()
}
