package ru.roguelike.model.generator.bsp

import org.junit.jupiter.api.Test
import ru.roguelike.model.RandomEnemyFactory

class BSPFieldGeneratorTest {

    @Test
    fun generatorStressTest() {
        (1..100).forEach { _ ->
            BSPFieldGenerator(RandomEnemyFactory()).generate()
        }
    }
}
