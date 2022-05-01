package ru.roguelike.logic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.roguelike.model.Cell
import ru.roguelike.model.CellType
import ru.roguelike.model.Coordinates
import ru.roguelike.model.Hero
import ru.roguelike.model.InventoryModel
import ru.roguelike.model.MapModel
import ru.roguelike.view.Drawable

class MapLogicTest {

    private fun getMapLogic(mapModelStr: String, N: Int, M: Int, hero: Hero = Hero(Coordinates(1, 1))): MapLogic {
        val field = MutableList(N) { MutableList(M) { Cell(CellType.NON_WALKABLE) } }
        for (i in 0 until N) {
            for (j in 0 until M) {
                if (mapModelStr[i * M + j] in ".*$") {
                    field[i][j] = Cell(CellType.WALKABLE)
                }
            }
        }
        val mapModel = MapModel(field)
        val mapView = object : Drawable {
            override fun draw() {}
        }
        val inventoryModel = InventoryModel()
        return MapLogic(hero, mapModel, inventoryModel, mapView)
    }

    @Test
    fun isHeroVisibleTest1() {
        val (N, M) = 6 to 6
        val mapModelStr = "" +
            "......" +
            "......" +
            "......" +
            "......" +
            "......" +
            "......"

        val mapLogic = getMapLogic(mapModelStr, N, M)

        for (i in 0 until N) {
            for (j in 0 until M) {
                Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(i, j)))
            }
        }
    }

    @Test
    fun isHeroVisibleTest2() {
        val (N, M) = 6 to 6
        val mapModelStr = "" +
            "......" +
            ".*.#.." +
            "...#.." +
            "..#..." +
            "......" +
            "......"

        val mapLogic = getMapLogic(mapModelStr, N, M)

        Assertions.assertFalse(mapLogic.isHeroVisible(Coordinates(4, 1)))
        Assertions.assertFalse(mapLogic.isHeroVisible(Coordinates(5, 1)))
        Assertions.assertFalse(mapLogic.isHeroVisible(Coordinates(4, 5)))
        Assertions.assertFalse(mapLogic.isHeroVisible(Coordinates(3, 3)))

        Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(1, 2)))
        Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(1, 3)))
        Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(1, 4)))
        Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(1, 5)))
        Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(3, 0)))
        Assertions.assertTrue(mapLogic.isHeroVisible(Coordinates(0, 0)))
    }
}
