package ru.roguelike.model.generator

import ru.roguelike.model.Cell
import ru.roguelike.model.CellType
import ru.roguelike.model.Field
import ru.roguelike.view.NON_WALKABLE_CHAR
import ru.roguelike.view.WALKABLE_CHAR
import java.io.FileInputStream

/**
 * Class that generate field by reading it from plain text file
 */
class TextFileFieldGenerator(private val path: String) : FieldGenerator {
    override fun generate(): Field =
        FileInputStream(path).use { input ->
            input.bufferedReader().use { reader ->
                generateSequence(reader::readLine).map { line ->
                    line.map { char ->
                        when (char) {
                            NON_WALKABLE_CHAR -> Cell(CellType.NON_WALKABLE)
                            WALKABLE_CHAR -> Cell(CellType.WALKABLE)
                            else -> error("Unsupported char")
                        }
                    }.toMutableList()
                }.toMutableList()
            }
        }
}
