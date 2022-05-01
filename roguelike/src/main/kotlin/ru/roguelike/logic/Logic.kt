package ru.roguelike.logic

/**
 * All processing operations do nothing by default
 */
interface Logic {
    /**
     * process moving left
     */
    fun moveLeft() {}

    /**
     * process moving right
     **/
    fun moveRight() {}

    /**
     * process moving up
     **/
    fun moveUp() {}

    /**
     * process moving down
     **/
    fun moveDown() {}

    /**
     * process drop
     */
    fun processDrop() {}

    /**
     * process equip
     */
    fun processEquip() {}

    /**
     * process unequip
     */
    fun processUnEquip() {}
    /**
     * draw current logic
     **/
    fun draw()

    /**
     * Check the deadness of hero
     */
    fun isHeroDead(): Boolean = false
}
