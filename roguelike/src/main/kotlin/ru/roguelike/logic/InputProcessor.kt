package ru.roguelike.logic

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

class InputProcessor(
    private val logicFacade: LogicFacade
) {

    /**
     *  process user actions
     */
    fun process(keyStroke: KeyStroke): Boolean {
        when (keyStroke.keyType) {
            KeyType.ArrowLeft -> logicFacade.processLeftArrow()
            KeyType.ArrowRight -> logicFacade.processRightArrow()
            KeyType.ArrowUp -> logicFacade.processUpArrow()
            KeyType.ArrowDown -> logicFacade.processDownArrow()
            KeyType.Character -> when (keyStroke.character.toChar()) {
                'h' -> logicFacade.processHelp()
                'm' -> logicFacade.processMap()
            }
            KeyType.EOF -> return false
            else -> return true
        }
        return true
    }
}
