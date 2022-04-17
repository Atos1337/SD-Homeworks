package ru.roguelike.model.generator

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.roguelike.model.Field
import java.io.FileInputStream

/**
 * Class that generate field by reading it from json file
 */
class JsonFileFieldGenerator(private val path: String) : FieldGenerator {
    override fun generate(): Field =
        FileInputStream(path).use {
            Json.decodeFromString(it.bufferedReader().readText())
        }
}
