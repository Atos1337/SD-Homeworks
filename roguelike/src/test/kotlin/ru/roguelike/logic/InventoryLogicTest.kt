package ru.roguelike.logic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.roguelike.model.Coordinates
import ru.roguelike.model.Hero
import ru.roguelike.model.InventoryModel
import ru.roguelike.model.Item
import ru.roguelike.model.MapModel
import ru.roguelike.model.Sword
import ru.roguelike.model.generator.generateFromInputStream
import ru.roguelike.util.Constants
import ru.roguelike.view.Drawable
import java.io.ByteArrayInputStream

class StubDrawable : Drawable {
    override fun draw() {
    }
}

class InventoryLogicTest {
    companion object {
        private fun createMapModel(str: String): MapModel {
            return MapModel(generateFromInputStream(ByteArrayInputStream(str.toByteArray())))
        }

        private fun createInventoryModel(items: List<Item>, inventorySize: Int = 5, maxEquipItems: Int = 0): InventoryModel {
            Constants.INVENTORY_SIZE = inventorySize
            Constants.MAXIMUM_EQUIPPED_ITEMS = maxEquipItems
            return InventoryModel(items.toMutableList())
        }

        fun createInventoryLogic(
            c: Coordinates,
            mapStr: String,
            items: List<Item>,
            inventorySize: Int = 5,
            maxEquipItems: Int = 0
        ): Triple<InventoryLogic, MapModel, InventoryModel> {
            val map = createMapModel(mapStr)
            val inventory = createInventoryModel(items, inventorySize, maxEquipItems)
            return Triple(
                InventoryLogic(
                    Hero(c),
                    inventory,
                    map,
                    StubDrawable()
                ),
                map, inventory
            )
        }
    }

    @Test
    fun tooManyEquippedTest() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(Sword(10))
        )
        Assertions.assertEquals(0, inventory.equippedItems.size)
        logic.processEquip()
        Assertions.assertEquals(0, inventory.equippedItems.size)
    }

    @Test
    fun nothingToEquipTest() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(),
            maxEquipItems = 1
        )
        Assertions.assertEquals(0, inventory.equippedItems.size)
        logic.processEquip()
        Assertions.assertEquals(0, inventory.equippedItems.size)
    }

    @Test
    fun currentItemEquippedTest() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(Sword(10)),
            maxEquipItems = 2
        )
        Assertions.assertEquals(0, inventory.equippedItems.size)
        logic.processEquip()
        Assertions.assertEquals(1, inventory.equippedItems.size)
        logic.processEquip()
        Assertions.assertEquals(1, inventory.equippedItems.size)
    }

    @Test
    fun nothingToUnequipTest() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(),
            maxEquipItems = 2
        )
        logic.processUnEquip()
    }

    @Test
    fun unequipUnusedItem() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(Sword(10)),
            maxEquipItems = 1
        )
        Assertions.assertEquals(0, inventory.equippedItems.size)
        logic.processUnEquip()
        Assertions.assertEquals(0, inventory.equippedItems.size)
    }

    @Test
    fun unequipUsedItem() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(Sword(10)),
            maxEquipItems = 1
        )
        logic.processEquip()
        Assertions.assertEquals(1, inventory.equippedItems.size)
        logic.processUnEquip()
        Assertions.assertEquals(0, inventory.equippedItems.size)
    }

    @Test
    fun dropOnBusyCell() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "@.",
            listOf(Sword(10)),
            maxEquipItems = 1
        )
        Assertions.assertEquals(1, inventory.items.size)
        logic.processDrop()
        Assertions.assertEquals(1, inventory.items.size)
    }

    @Test
    fun dropOnEmptyCell() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(Sword(10)),
            maxEquipItems = 1
        )
        Assertions.assertEquals(1, inventory.items.size)
        logic.processDrop()
        Assertions.assertEquals(0, inventory.items.size)
    }

    @Test
    fun dropEquippedItemTest() {
        val (logic, map, inventory) = createInventoryLogic(
            Coordinates(0, 0),
            "..",
            listOf(Sword(10)),
            maxEquipItems = 1
        )
        logic.processEquip()
        Assertions.assertEquals(1, inventory.items.size)
        logic.processDrop()
        Assertions.assertEquals(1, inventory.items.size)
    }
}
