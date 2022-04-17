package ru.roguelike.logic

import ru.roguelike.view.InstructionsView

class InstructionsLogic(
    private val view: InstructionsView
) : Logic {
    /**
     * process moving left, do nothing
     */
    override fun moveLeft() {
        return
    }

    /**
     * process moving right, do nothing
     **/
    override fun moveRight() {
        return
    }

    /**
     * process moving up, do nothing
     **/
    override fun moveUp() {
        return
    }

    /**
     * process moving down, do nothing
     **/
    override fun moveDown() {
        return
    }

    /**
     * draw help
     **/
    override fun draw() {
        view.draw()
    }
}
