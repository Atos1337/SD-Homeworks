package ru.roguelike.model.generator

import ru.roguelike.model.Field
import java.io.FileInputStream

/**
 * Class that generate field by reading it from plain text file
 */
class TextFileFieldGenerator(private val path: String) : FieldGenerator {
    override fun generate(): Field =
        FileInputStream(path).use { input ->
            generateFromInputStream(input)
        }
}
