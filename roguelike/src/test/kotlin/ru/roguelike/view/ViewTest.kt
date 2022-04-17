package ru.roguelike.view

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import ru.roguelike.model.CellType
import ru.roguelike.model.Character
import ru.roguelike.model.MapModel
import ru.roguelike.util.Constants

class ViewTest {
    @Disabled("Can't create terminal in CI")
    @Test
    fun testLeft() {
        val mapModel = MapModel()
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
        val character = Character(coordinates)
        val characterView = CharacterView(character, screen)
        val mapView = MapView(mapModel, screen, characterView)

        val constMap = mapOf(Pair(CellType.NON_WALKABLE, NON_WALKABLE_CHAR), Pair(CellType.WALKABLE, WALKABLE_CHAR))
        terminal.use {
            screen.use {
                mapView.draw()
                for (row in 0 until screen.terminal.terminalSize.rows - 2) {
                    for (column in 0 until screen.terminal.terminalSize.columns) {
                        if (character.coordinates.x == column && character.coordinates.y == row)
                            Assertions.assertEquals(
                                screen.getBackCharacter(column, row).characterString[0],
                                CHARACTER_CHAR
                            )
                        else
                            Assertions.assertEquals(
                                screen.getBackCharacter(column, row).characterString[0],
                                constMap[mapModel.field[row][column].cellType]
                            )
                    }
                }
            }
        }
    }
}
