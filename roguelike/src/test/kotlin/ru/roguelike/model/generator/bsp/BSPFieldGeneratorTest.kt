package ru.roguelike.model.generator.bsp

import org.junit.jupiter.api.Test

class BSPFieldGeneratorTest {

    @Test
    fun generatorStressTest() {
        (1..100).forEach { _ ->
            BSPFieldGenerator.generate()
        }
    }
}
