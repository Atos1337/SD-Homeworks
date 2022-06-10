package ru.roguelike.logic

import ru.roguelike.model.DisposableItem
import ru.roguelike.model.Hero
import ru.roguelike.model.InventoryModel
import ru.roguelike.model.MapModel
import ru.roguelike.model.NonDisposableItem
import ru.roguelike.util.Constants
import ru.roguelike.view.Drawable

/**
 * Class that stores logic about inventory
 */
class InventoryLogic(
    private val character: Hero,
    private val inventoryModel: InventoryModel,
    private val mapModel: MapModel,
    private val view: Drawable
) : Logic {
    /**
     * processing equip current item
     */
    override fun processEquip() {
        inventoryModel.getCurrentItem()?.let { item ->
            if (!inventoryModel.isCurrentItemEquipped()) {
                if (inventoryModel.equippedItems.size >= Constants.MAXIMUM_EQUIPPED_ITEMS && item !is DisposableItem) {
                    view.setError("You already equip too much items")
                    return
                }
                if (character.use(item)) {
                    inventoryModel.useCurrentItem()
                } else {
                    view.setError("You cant pick it !")
                }
            } else {
                view.setError("Current item already equipped")
            }
        }
    }

    /**
     * processing unequip current item
     */
    override fun processUnEquip() {
        inventoryModel.getCurrentItem()?.let { item ->
            if (item is NonDisposableItem && inventoryModel.isCurrentItemEquipped()) {
                character.unUse(item)
                inventoryModel.unUseCurrentItem()
            } else {
                view.setError("Item is disposable or is not equipped")
            }
        }
    }

    /**
     * processing dropping item from inventory
     */
    override fun processDrop() {
        if (mapModel.field[character.coordinates.y][character.coordinates.x].item != null) {
            view.setError("Item already exists on current map cell")
            return
        }

        inventoryModel.getCurrentItem()?.let { item ->
            if (!inventoryModel.isCurrentItemEquipped()) {
                inventoryModel.dropCurrentItem()
                mapModel.field[character.coordinates.y][character.coordinates.x].item = item
            } else {
                view.setError("You can't drop equipped item")
            }
        }
    }

    /**
     * processing moving up in inventory
     */
    override fun moveUp() {
        inventoryModel.moveUp()
    }

    /**
     * processing moving down in inventory
     */
    override fun moveDown() {
        inventoryModel.moveDown()
    }

    /**
     * Draw inventory
     */
    override fun draw() {
        view.draw()
    }
}
