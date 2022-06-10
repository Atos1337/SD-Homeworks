package ru.roguelike.command

import ru.roguelike.logic.GameState
import ru.roguelike.logic.LogicFacade

/**
 * Class, that contains common logic of all commands
 */
abstract class CommandImpl(protected val logicFacade: LogicFacade) : Command {
    override fun execute(): GameState {
        if (logicFacade.isHeroDead()) {
            return GameState.DEAD_HERO
        }
        return executeImpl()
    }

    protected abstract fun executeImpl(): GameState
}

/**
 * Command, which processing left move
 */
class MoveLeftCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.moveLeft()
        return GameState.GOOD
    }
}

/**
 * Command, which processing right move
 */
class MoveRightCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.moveRight()
        return GameState.GOOD
    }
}

/**
 * Command, which processing up move
 */
class MoveUpCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.moveUp()
        return GameState.GOOD
    }
}

/**
 * Command, which processing down move
 */
class MoveDownCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.moveDown()
        return GameState.GOOD
    }
}

/**
 * Command, which change current logic on help
 */
class TurnHelpCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.processHelp()
        return GameState.GOOD
    }
}

/**
 * Command, which change current logic on map
 */
class TurnMapCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.processMap()
        return GameState.GOOD
    }
}

/**
 * Command, which change current logic on inventory
 */
class TurnInventoryCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.processInventory()
        return GameState.GOOD
    }
}

/**
 * Command, which processing equip
 */
class EquipCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.processEquip()
        return GameState.GOOD
    }
}

/**
 * Command, which processing unequip
 */
class UnEquipCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.processUnEquip()
        return GameState.GOOD
    }
}

/**
 * Command, which processing drop
 */
class DropCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState {
        logicFacade.processDrop()
        return GameState.GOOD
    }
}

/**
 * Command, which processing end of input
 */
class EOFCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState = GameState.TERMINATED
}

/**
 * Unknown command always returns good
 */
class UnknownCommand(logicFacade: LogicFacade) : CommandImpl(logicFacade) {
    override fun executeImpl(): GameState = GameState.GOOD
}
