package ru.roguelike.view

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.screen.Screen
import ru.roguelike.model.InstructionModel
import java.lang.Integer.min

/**
 * This interface provides single function for drawing object.
 */
class InstructionsView(
    private val instructions: InstructionModel,
    private val screen: Screen
) : Drawable {
    /**
     * This method draws the instructions.
     */
    override fun draw() {
        screen.clear()
        val description = instructions.description
        var authors = ""
        for (author in instructions.authors) {
            authors += if (authors != "")
                ", $author"
            else
                author
        }
        val instructionPairs = MutableList(instructions.instructions.size) { "" }
        for (instructionName in instructions.instructions.keys) {
            instructionPairs.add(instructionName + ": " + instructions.instructions[instructionName])
        }
        var row = 0
        row = addStringToScreen(row, description) + 1
        row = addStringToScreen(row, authors) + 1
        for (str in instructionPairs) {
            row = addStringToScreen(row, str)
        }
        screen.refresh()
    }

    private fun addStringToScreen(beginRow: Int, str: String): Int {
        var currentRow = beginRow
        var stringPos = 0
        val screenLen = screen.terminalSize.columns
        while (stringPos < str.length) {
            val currentString = str.subSequence(
                stringPos,
                min(stringPos + screenLen, str.length)
            )
            for (column in currentString.indices) {
                screen.setCharacter(
                    column, currentRow,
                    TextCharacter.fromCharacter(
                        currentString[column]
                    )[0]
                )
            }
            currentRow += 1
            stringPos = (currentRow - beginRow) * screenLen
        }
        return currentRow
    }
}
