package ru.cli

import ru.cli.commands.Command
import ru.cli.commands.CommandFactory
import ru.cli.commands.StatusCode
import java.lang.Exception

fun main() {
    val commandFactory = CommandFactory()
    val environment = Environment()

    generateSequence(::readLine).forEach { line ->
        if (line.isEmpty()) {
            return@forEach
        }

        val tokens = Parser.splitIntoTokens(line).flatMap { token ->
            Substitutor.substitute(token, environment).let { substitutedToken ->
                when (substitutedToken.quotingType) {
                    QuotingType.WITHOUT_QUOTE -> Parser.splitIntoTokens(substitutedToken.value)
                    else -> listOf(substitutedToken)
                }
            }
        }

        lateinit var commands: List<Command>

        try {
            commands = Parser.splitIntoCommands(tokens).map { commandFactory.getCommand(it) }
        } catch (e: IllegalStateException) {
            System.err.println(e.message)
            return@forEach
        }

        val pipe = Pipe(commands)

        try {
            when (pipe.execute(environment = environment).status) {
                StatusCode.EXIT -> return
                else -> Unit
            }
        } catch (e: Exception) {
            System.err.println(e.message)
        }
    }
}
