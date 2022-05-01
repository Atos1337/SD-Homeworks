package ru.roguelike.view

/**
 * This interface provides single function for drawing object.
 */
interface Drawable {
    fun draw()
    fun setError(message: String) {}
}
