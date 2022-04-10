package ru.roguelike.logic

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType


class InputProcessor(
    private val logicFacade: LogicFacade
) {

    fun process(keyStroke: KeyStroke) {
        when (keyStroke.keyType) {
            KeyType.ArrowLeft -> logicFacade.processLeftArrow()
            KeyType.ArrowRight -> logicFacade.processRightArrow()
            KeyType.ArrowUp -> logicFacade.processUpArrow()
            KeyType.ArrowDown -> logicFacade.processDownArrow()
        }
    }

}