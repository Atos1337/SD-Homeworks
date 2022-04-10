package ru.roguelike

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import ru.roguelike.logic.InputProcessor
import ru.roguelike.logic.InstructionsLogic
import ru.roguelike.logic.LogicFacade
import ru.roguelike.logic.MapLogic
import ru.roguelike.model.Character
import ru.roguelike.model.InstructionModel
import ru.roguelike.model.MapModel
import ru.roguelike.view.*


fun main() {
    val mapModel = MapModel()
    val defaultTerminalFactory = DefaultTerminalFactory().also {
        it.setInitialTerminalSize(
            TerminalSize(mapModel.field.size, mapModel.field[0].size + 2)
        )
    }
    val terminal = defaultTerminalFactory.createTerminal()
    val screen = TerminalScreen(terminal).also {
        it.startScreen(); it.cursorPosition = null
    }
    val character = Character()
    val characterView = CharacterView(character, screen)
    val mapView = MapView(mapModel, screen, characterView)
    val mapLogic = MapLogic(character, mapModel, mapView)

    val instructionsModel = InstructionModel(
        INSTRUCTIONS,
        DESCRIPTION,
        AUTHORS
    )

    val instructionsView = InstructionsView(instructionsModel, screen)
    val instructionsLogic = InstructionsLogic(instructionsView)

    val logicFacade = LogicFacade(map = mapLogic, instructions = instructionsLogic)
    val inputProcessor = InputProcessor(logicFacade)

    terminal.use {
        screen.use {
            mapView.draw()
            while (true) {
                val keyStroke = it.readInput()
                val isNotTerminated = inputProcessor.process(keyStroke)
                if (!isNotTerminated) {
                    break
                }
            }
        }
    }
}
