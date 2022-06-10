package ru.roguelike.model.generator

import ru.roguelike.logic.AgressiveStrategy
import ru.roguelike.logic.PassiveStrategy
import ru.roguelike.logic.SneakyStrategy
import ru.roguelike.model.Apple
import ru.roguelike.model.Cell
import ru.roguelike.model.CellType
import ru.roguelike.model.Coordinates
import ru.roguelike.model.Enemy
import ru.roguelike.model.EnemyStyle
import ru.roguelike.model.Shield
import ru.roguelike.model.Sword
import ru.roguelike.util.Constants
import ru.roguelike.view.AGRESSIVE_CHAR
import ru.roguelike.view.APPLE_CHAR
import ru.roguelike.view.NON_WALKABLE_CHAR
import ru.roguelike.view.PASSIVE_CHAR
import ru.roguelike.view.SHIELD_CHAR
import ru.roguelike.view.SNEAKY_CHAR
import ru.roguelike.view.SWORD_CHAR
import ru.roguelike.view.WALKABLE_CHAR
import java.io.InputStream
import kotlin.random.Random
import kotlin.random.nextInt

fun Char.toCell(c: Coordinates): Cell =
    when (this) {
        NON_WALKABLE_CHAR -> Cell(CellType.NON_WALKABLE)
        WALKABLE_CHAR -> Cell(CellType.WALKABLE)
        SWORD_CHAR -> Cell(CellType.WALKABLE, Sword(Random.nextInt(1..Constants.MAX_ENEMY_DAMAGE)))
        APPLE_CHAR -> Cell(CellType.WALKABLE, Apple(Random.nextInt(1..Constants.MAX_ENEMY_HP)))
        SHIELD_CHAR -> Cell(CellType.WALKABLE, Shield(Random.nextInt(1..Constants.MAX_ARMOR)))
        AGRESSIVE_CHAR -> Cell(CellType.WALKABLE, null, Enemy(c, Random.nextInt(1..Constants.MAX_ENEMY_HP), AgressiveStrategy(), EnemyStyle.DRAGON))
        PASSIVE_CHAR -> Cell(CellType.WALKABLE, null, Enemy(c, Random.nextInt(1..Constants.MAX_ENEMY_HP), PassiveStrategy(), EnemyStyle.DRAGON))
        SNEAKY_CHAR -> Cell(CellType.WALKABLE, null, Enemy(c, Random.nextInt(1..Constants.MAX_ENEMY_HP), SneakyStrategy(), EnemyStyle.DRAGON))
        else -> error("unsupported character")
    }

fun generateFromInputStream(input: InputStream) =
    input.bufferedReader().use { reader ->
        generateSequence(reader::readLine).mapIndexed { row, line ->
            line.mapIndexed { column, char ->
                char.toCell(Coordinates(column, row))
            }.toMutableList()
        }.toMutableList()
    }
