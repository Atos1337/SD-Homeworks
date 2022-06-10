package ru.roguelike.view

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.roguelike.model.Apple
import ru.roguelike.model.CellType
import ru.roguelike.model.Hero
import ru.roguelike.model.InventoryModel
import ru.roguelike.model.Item
import ru.roguelike.model.MapModel
import ru.roguelike.model.RandomEnemyFactory
import ru.roguelike.model.Shield
import ru.roguelike.model.Sword
import ru.roguelike.model.generator.FieldBuilder
import ru.roguelike.util.Constants

class ViewTest {
    @Disabled("Can't create terminal in CI")
    @Test
    fun testMapView() {
        val field = FieldBuilder(RandomEnemyFactory()).build()
        val mapModel = MapModel(field)
        val defaultTerminalFactory = DefaultTerminalFactory().also {
            it.setInitialTerminalSize(
                TerminalSize(Constants.FIELD_WIDTH, Constants.FIELD_HEIGHT + Constants.CHARACTER_VIEW_HEIGHT)
            )
        }
        val terminal = defaultTerminalFactory.createTerminal()
        val screen = TerminalScreen(terminal).also {
            it.startScreen(); it.cursorPosition = null
        }
        val coordinates = mapModel.getRandomWalkableCoordinates()
        val character = Hero(coordinates)
        val characterView = CharacterView(character, screen)
        val mapView = MapView(mapModel, screen, characterView)

        val constMap = mapOf(Pair(CellType.NON_WALKABLE, NON_WALKABLE_CHAR), Pair(CellType.WALKABLE, WALKABLE_CHAR))
        terminal.use {
            screen.use {
                mapView.draw()
                for (row in 1 until screen.terminal.terminalSize.rows - 3) {
                    for (column in 0 until screen.terminal.terminalSize.columns) {
                        if (character.coordinates.x == column && character.coordinates.y + 1 == row)
                            Assertions.assertEquals(
                                screen.getBackCharacter(column, row).characterString[0],
                                CHARACTER_CHAR
                            )
                        else {
                            if (mapModel.field[row - 1][column].item != null) {
                                Assertions.assertEquals(
                                    screen.getBackCharacter(column, row).characterString[0],
                                    when (mapModel.field[row - 1][column].item) {
                                        is Shield -> TextCharacter.fromCharacter(SHIELD_CHAR)[0].characterString[0]
                                        is Sword -> TextCharacter.fromCharacter(SWORD_CHAR)[0].characterString[0]
                                        is Apple -> TextCharacter.fromCharacter(APPLE_CHAR)[0].characterString[0]
                                        else -> {
                                            TextCharacter.fromCharacter(NON_WALKABLE_CHAR)[0].characterString[0]
                                        }
                                    }
                                )
                            } else if (mapModel.field[row - 1][column].enemy != null) {
                                Assertions.assertEquals(
                                    screen.getBackCharacter(column, row).characterString[0],
                                    TextCharacter.fromCharacter(mapModel.field[row - 1][column].enemy.toString()[0])[0].characterString[0]
                                )
                            } else {
                                Assertions.assertEquals(
                                    screen.getBackCharacter(column, row).characterString[0],
                                    constMap[mapModel.field[row - 1][column].cellType]
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Disabled("Can't create terminal in CI")
    @Test
    fun testInventoryView() {
        val field = FieldBuilder(RandomEnemyFactory()).build()
        val defaultTerminalFactory = DefaultTerminalFactory().also {
            it.setInitialTerminalSize(
                TerminalSize(Constants.FIELD_WIDTH, Constants.FIELD_HEIGHT + Constants.CHARACTER_VIEW_HEIGHT)
            )
        }
        val terminal = defaultTerminalFactory.createTerminal()
        val screen = TerminalScreen(terminal).also {
            it.startScreen(); it.cursorPosition = null
        }
        val inventoryModel = InventoryModel(listOf(Shield(2), Sword(3), Apple(4)) as MutableList<Item>)
        val mapModel = MapModel(field)
        val coordinates = mapModel.getRandomWalkableCoordinates()
        val character = Hero(coordinates)
        val characterView = CharacterView(character, screen)
        val inventoryView = InventoryView(inventoryModel, characterView, screen)
        terminal.use {
            screen.use {
                inventoryView.draw()
                for (row in 0 until inventoryModel.items.size) {
                    Assertions.assertEquals(
                        screen.getBackCharacter(0, row).characterString[0],
                        when (inventoryModel.items[row]) {
                            is Shield -> TextCharacter.fromCharacter(SHIELD_CHAR)[0].characterString[0]
                            is Sword -> TextCharacter.fromCharacter(SWORD_CHAR)[0].characterString[0]
                            is Apple -> TextCharacter.fromCharacter(APPLE_CHAR)[0].characterString[0]
                            else -> {
                                TextCharacter.fromCharacter(NON_WALKABLE_CHAR)[0].characterString[0]
                            }
                        }
                    )
                    for (column in 2 until inventoryModel.items[row].toString().length) {
                        Assertions.assertEquals(
                            screen.getBackCharacter(column, row).characterString[0],
                            inventoryModel.items[row].toString()[column - 2]
                        )
                    }
                }
            }
        }
    }
}
