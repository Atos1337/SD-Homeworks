package ru.roguelike.model.generator

import ru.roguelike.model.Field

/**
 * Interface for map generation
 */
interface FieldGenerator {
    fun generate(): Field
}
