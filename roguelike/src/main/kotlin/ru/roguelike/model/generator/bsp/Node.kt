package ru.roguelike.model.generator.bsp

import ru.roguelike.model.Cell
import ru.roguelike.model.CellType
import ru.roguelike.model.Coordinates
import ru.roguelike.model.Field
import ru.roguelike.util.Constants
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Class that represents node of BSPTree
 */
internal class Node(
    private val x: Int,
    private val y: Int,
    private val width: Int,
    private val height: Int
) {
    private var leftChild: Node? = null
    private var rightChild: Node? = null
    private val tunnels: MutableList<Tunnel> = mutableListOf()
    private var _room: Room? = null
    private val room: Room?
        get() =
            _room ?: run {
                val leftRoom = leftChild?.room
                val rightRoom = rightChild?.room
                when (Random.nextBoolean()) {
                    true -> leftRoom ?: rightRoom
                    false -> rightRoom ?: leftRoom
                }
            }

    private enum class SplitType {
        HORIZONTALLY,
        VERTICALLY
    }

    private val splitType: SplitType = run {
        var splitType = when (Random.nextBoolean()) {
            true -> SplitType.HORIZONTALLY
            false -> SplitType.VERTICALLY
        }

        if (width > height && width / height >= 1.25)
            splitType = SplitType.VERTICALLY
        else if (height > width && height / width >= 1.25)
            splitType = SplitType.HORIZONTALLY

        splitType
    }

    /**
     * Split space in two subspaces
     */
    fun split() {
        val max = when (splitType) {
            SplitType.HORIZONTALLY -> height
            SplitType.VERTICALLY -> width
        } - Constants.MIN_CELL_SIZE

        if (max < Constants.MIN_CELL_SIZE)
            return

        val splitSize = Random.nextInt(Constants.MIN_CELL_SIZE..max)

        check(splitSize >= Constants.MIN_CELL_SIZE)

        when (splitType) {
            SplitType.HORIZONTALLY -> {
                check(height - splitSize >= Constants.MIN_CELL_SIZE) { "$height, $splitSize" }
                leftChild = Node(x, y, width, splitSize)
                rightChild = Node(x, y + splitSize, width, height - splitSize)
            }
            SplitType.VERTICALLY -> {
                check(width - splitSize >= Constants.MIN_CELL_SIZE) { "$width, $splitSize" }
                leftChild = Node(x, y, splitSize, height)
                rightChild = Node(x + splitSize, y, width - splitSize, height)
            }
        }
        leftChild?.trySplit()
        rightChild?.trySplit()
    }

    private fun trySplit() {
        if (width > Constants.MAX_CELL_SIZE || height > Constants.MAX_CELL_SIZE ||
            Random.nextDouble() > 0.25
        )
            split()
    }

    /**
     * Create rooms in leaves of BSPTree
     */
    fun createRooms() {
        if (leftChild != null || rightChild != null) {
            leftChild?.createRooms()
            rightChild?.createRooms()

            leftChild?.room?.let { l ->
                rightChild?.room?.let { r ->
                    Tunnel.createTunnels(l, r)
                }
            }?.let {
                tunnels += it
            }
        } else {
            val roomWidth = Random.nextInt(Constants.ROOM_SIZE, width - 1)
            val roomHeight = Random.nextInt(Constants.ROOM_SIZE, height - 1)
            val roomCoordinates = Coordinates(
                Random.nextInt(1, width - roomWidth),
                Random.nextInt(1, height - roomHeight)
            )

            _room = Room(Coordinates(x, y) + roomCoordinates, roomWidth, roomHeight)
        }
    }

    /**
     * Traverse BSPTree and fill field with walkable cells
     */
    fun fillField(field: Field) {
        _room?.let {
            (it.leftBottom.y until (it.leftBottom.y + it.height)).forEach { row ->
                (it.leftBottom.x until (it.leftBottom.x + it.width)).forEach { column ->
                    field[row][column] = Cell(CellType.WALKABLE)
                }
            }
        }

        tunnels.forEach { tunnel ->
            when (tunnel.direction) {
                DirectionType.HORIZONTAL -> {
                    check(tunnel.coordinates.x + tunnel.length <= Constants.FIELD_WIDTH) {
                        "${tunnel.coordinates.x}, ${tunnel.length}"
                    }
                    (tunnel.coordinates.x until tunnel.coordinates.x + tunnel.length).forEach { column ->
                        field[tunnel.coordinates.y][column] = Cell(CellType.WALKABLE)
                    }
                }
                DirectionType.VERTICAL -> {
                    check(tunnel.coordinates.y + tunnel.length <= Constants.FIELD_HEIGHT) {
                        "${tunnel.coordinates.y}, ${tunnel.length}"
                    }
                    (tunnel.coordinates.y until tunnel.coordinates.y + tunnel.length).forEach { row ->
                        field[row][tunnel.coordinates.x] = Cell(CellType.WALKABLE)
                    }
                }
            }
        }

        leftChild?.fillField(field)
        rightChild?.fillField(field)
    }
}
