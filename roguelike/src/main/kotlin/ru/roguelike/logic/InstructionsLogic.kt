package ru.roguelike.logic

import ru.roguelike.view.Drawable

/**
 * Class that stores logic about instructions
 */
class InstructionsLogic(
    private val view: Drawable
) : Logic {
    /**
     * draw help
     **/
    override fun draw() {
        view.draw()
    }
}
