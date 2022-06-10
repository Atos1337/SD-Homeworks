package ru.roguelike.command

import ru.roguelike.logic.GameState

/**
 * Common interfaces for all commands
 */
interface Command {
    /**
     * Execute some action depends on semantics of the command
     */
    fun execute(): GameState
}
