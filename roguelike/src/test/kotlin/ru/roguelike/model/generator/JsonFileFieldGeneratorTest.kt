package ru.roguelike.model.generator

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.roguelike.model.EnemyFactoryImpl
import ru.roguelike.model.Field
import java.nio.file.Paths

internal class JsonFileFieldGeneratorTest {
    @Test
    fun simpleTest() {
        val fieldPath = Paths.get("src", "test", "resources", "field.json")
        val expectedField: Field = fieldPath.toFile().bufferedReader().use {
            Json.decodeFromString(it.readText())
        }

        Assertions.assertEquals(expectedField, FieldBuilder(EnemyFactoryImpl()).fromFile(fieldPath.toFile().absolutePath).build())
    }
}
