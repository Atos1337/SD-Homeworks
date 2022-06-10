package ru.roguelike.util

import ru.roguelike.model.EnemyStyle

/**
 * Class that stores project constants
 */
object Constants {
    var FIELD_WIDTH = 100
    var FIELD_HEIGHT = 30
    var CHARACTER_VIEW_HEIGHT = 4
    var ERROR_VIEW_HEIGHT = 1

    var MIN_CELL_SIZE = 10
    var MAX_CELL_SIZE = 15
    var ROOM_SIZE = 4

    var INVENTORY_SIZE = 5
    var MAXIMUM_EQUIPPED_ITEMS = 2

    var ROOM_ENEMY_COUNT = 2
    var ROOM_ITEMS_COUNT = 3

    var MAX_ENEMY_HP = 70
    var MAX_ENEMY_DAMAGE = 30
    var MAX_ARMOR = 50

    var HP_INCREASE_FOR_LEVEL = 10
    var DAMAGE_INCREASE_FOR_LEVEL = 5
    var EXP_FOR_LEVEL_UP = 500

    var CONFUSE_PROBABILITY = 0.1

    var CLONE_PROBABILITY = 0.3
    var CLONABLE_ENEMIES = setOf(EnemyStyle.SKELETON)

    var SKELETON_PROBABILITY = 0.14
    var DRAGON_PROBABILITY = 0.43
    var CYBORG_PROBABILITY = 0.43

    var SKELETON_REGEN = 0
    var DRAGON_REGEN = 1
    var CYBORG_REGEN = 2

    var PANIC_HP_BOUND = 0.3
}
