package ru.roguelike.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.roguelike.util.Constants

class InventoryModelTest {
    @Test
    fun moveTest() {
        Constants.INVENTORY_SIZE = 2
        val item = Items.createRandomItem()
        val model = InventoryModel(mutableListOf(item))
        Assertions.assertEquals(0, model.currentItemIndex)
        model.moveUp()
        Assertions.assertEquals(0, model.currentItemIndex)
        Assertions.assertEquals(item, model.getCurrentItem())
        model.moveDown()
        Assertions.assertEquals(1, model.currentItemIndex)
        model.moveDown()
        Assertions.assertEquals(1, model.currentItemIndex)
        Assertions.assertEquals(null, model.getCurrentItem())
        model.moveUp()
        Assertions.assertEquals(item, model.getCurrentItem())
    }

    @Test
    fun addTest() {
        val model = InventoryModel()
        Assertions.assertEquals(0, model.items.size)
        model.addItem(Items.createRandomItem())
        Assertions.assertEquals(1, model.items.size)
    }

    @Test
    fun useTest() {
        val model = InventoryModel(mutableListOf(Sword(10)))
        Assertions.assertEquals(0, model.equippedItems.size)
        model.useCurrentItem()
        Assertions.assertEquals(1, model.equippedItems.size)
        model.unUseCurrentItem()
        Assertions.assertEquals(0, model.equippedItems.size)
        Assertions.assertEquals(1, model.items.size)
        model.dropCurrentItem()
        Assertions.assertEquals(0, model.items.size)
        model.addItem(Apple(10))
        model.useCurrentItem()
        Assertions.assertEquals(0, model.items.size)
        Assertions.assertEquals(0, model.equippedItems.size)
    }
}
