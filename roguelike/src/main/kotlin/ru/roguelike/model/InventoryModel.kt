package ru.roguelike.model

import ru.roguelike.util.Constants

/**
 *  Class that store information about inventory
 */
class InventoryModel(private val items_: MutableList<Item> = mutableListOf()) {
    val items: List<Item> get() = items_
    var currentItemIndex: Int = 0
        private set
    private var equippedItems_: MutableSet<Int> = mutableSetOf()
    val equippedItems: Set<Int> get() = equippedItems_

    /**
     * Point the neighbor item from inventory, which is downer then current
     */
    fun moveDown() {
        if (currentItemIndex < Constants.INVENTORY_SIZE - 1) {
            currentItemIndex++
        }
    }

    /**
     * Point the neighbor item from inventory, which is upper then current
     */
    fun moveUp() {
        if (currentItemIndex > 0) {
            currentItemIndex--
        }
    }

    /**
     * Choose the current element
     */
    fun getCurrentItem(): Item? {
        return if (currentItemIndex < items_.size) items_[currentItemIndex] else null
    }

    /**
     * UnUse current item in inventory. Invariant: current item is equipped
     */
    fun unUseCurrentItem() {
        equippedItems_.remove(currentItemIndex)
    }

    /**
     * Use current item in inventory. Invariant: current item is not equipped
     */
    fun useCurrentItem() {
        when (items_[currentItemIndex]) {
            is DisposableItem -> dropCurrentItem()
            is NonDisposableItem -> equippedItems_.add(currentItemIndex)
        }
    }

    /**
     * Drop current item from inventory to map. Invariant: current item is not equipped
     */
    fun dropCurrentItem() {
        items_.removeAt(currentItemIndex)
        equippedItems_ = equippedItems_.map {
            if (it > currentItemIndex) {
                it - 1
            } else {
                it
            }
        }.toMutableSet()
    }

    /**
     * Add item to inventory
     */
    fun addItem(item: Item) {
        items_.add(item)
    }

    /**
     * Checks whether current item is already equipped
     */
    fun isCurrentItemEquipped(): Boolean {
        return equippedItems_.contains(currentItemIndex)
    }
}
