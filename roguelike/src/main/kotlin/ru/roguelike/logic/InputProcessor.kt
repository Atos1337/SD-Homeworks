package ru.roguelike.logic

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType

class InputProcessor(
    private val logicFacade: LogicFacade
) {

    /**
     *  H user actions
     */
    fun process(keyStroke: KeyStroke): GameState {
        if (logicFacade.isHeroDead()) {
            return GameState.DEAD_HERO
        }
        when (keyStroke.keyType) {
            KeyType.ArrowLeft -> logicFacade.moveLeft()
            KeyType.ArrowRight -> logicFacade.moveRight()
            KeyType.ArrowUp -> logicFacade.moveUp()
            KeyType.ArrowDown -> logicFacade.moveDown()
            KeyType.Character -> when (keyStroke.character.toChar()) {
                'h' -> logicFacade.processHelp()
                'm' -> logicFacade.processMap()
                'i' -> logicFacade.processInventory()
                'e' -> logicFacade.processEquip()
                'u' -> logicFacade.processUnEquip()
                'd' -> logicFacade.processDrop()
            }
            KeyType.EOF -> return GameState.TERMINATED
            else -> return GameState.GOOD
        }
        return GameState.GOOD
    }
}
