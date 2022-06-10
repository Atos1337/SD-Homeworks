package ru.roguelike.model.generator.bsp

import ru.roguelike.model.Cell
import ru.roguelike.model.CellType
import ru.roguelike.model.EnemyFactory
import ru.roguelike.model.Field
import ru.roguelike.model.generator.FieldGenerator
import ru.roguelike.util.Constants

/**
 * Class that generate field using BSPTree
 */
class BSPFieldGenerator(private val enemyFactory: EnemyFactory) : FieldGenerator {
    override fun generate(): Field {
        val field = MutableList(Constants.FIELD_HEIGHT) {
            MutableList(Constants.FIELD_WIDTH) { Cell(CellType.NON_WALKABLE) }
        }
        val root = Node(0, 0, Constants.FIELD_WIDTH, Constants.FIELD_HEIGHT)
        root.split()
        root.createRooms(enemyFactory)
        root.fillField(field)

        return field
    }
}
