package ru.roguelike.model

typealias Field = MutableList<MutableList<Cell>>

/**
 * Class that store information about map
 */
class MapModel(
    val field: Field
) {
    fun getX() = field[0].size
    fun getY() = field.size

    /**
     * Check whether cell with that coordinates is walkable
     */
    fun isWalkable(coordinates: Coordinates) = field[coordinates.y][coordinates.x].cellType == CellType.WALKABLE

    /**
     * Check whether cell with that coordinates is without enemy
     */
    fun isWithoutEnemy(coordinates: Coordinates) = field[coordinates.y][coordinates.x].enemy == null

    /**
     * Find first walkable cell
     */
    fun getRandomWalkableCoordinates(): Coordinates {
        for (row in 0 until getY()) {
            for (col in 0 until getX()) {
                if (field[row][col].cellType == CellType.WALKABLE) {
                    return Coordinates(col, row)
                }
            }
        }
        throw Exception()
    }
}
