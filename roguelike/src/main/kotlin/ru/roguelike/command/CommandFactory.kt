package ru.roguelike.command

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import ru.roguelike.logic.LogicFacade

/**
 * Class, that produces command by keyStroke and logicFacade
 */
object CommandFactory {
    fun getCommand(keyStroke: KeyStroke, logicFacade: LogicFacade): Command =
        when (keyStroke.keyType) {
            KeyType.ArrowLeft -> MoveLeftCommand(logicFacade)
            KeyType.ArrowRight -> MoveRightCommand(logicFacade)
            KeyType.ArrowUp -> MoveUpCommand(logicFacade)
            KeyType.ArrowDown -> MoveDownCommand(logicFacade)
            KeyType.Character -> when (keyStroke.character.toChar()) {
                'h' -> TurnHelpCommand(logicFacade)
                'm' -> TurnMapCommand(logicFacade)
                'i' -> TurnInventoryCommand(logicFacade)
                'e' -> EquipCommand(logicFacade)
                'u' -> UnEquipCommand(logicFacade)
                'd' -> DropCommand(logicFacade)
                else -> UnknownCommand(logicFacade)
            }
            KeyType.EOF -> EOFCommand(logicFacade)
            else -> UnknownCommand(logicFacade)
        }
}
