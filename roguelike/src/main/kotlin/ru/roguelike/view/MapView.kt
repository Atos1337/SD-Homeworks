package ru.roguelike.view

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.screen.Screen
import ru.roguelike.model.Apple
import ru.roguelike.model.CellType
import ru.roguelike.model.EnemyStyle
import ru.roguelike.model.MapModel
import ru.roguelike.model.Shield
import ru.roguelike.model.Sword
import ru.roguelike.util.Constants.ERROR_VIEW_HEIGHT

/**
 * This class provides one method which draws map and character.
 */
class MapView(
    private val map: MapModel,
    private val screen: Screen,
    private val characterView: CharacterView
) : Drawable {
    private val errorView = ErrorView(screen)
    override fun setError(message: String) {
        errorView.message = message
    }
    /**
     * This method draws map and character.
     */
    override fun draw() {
        screen.clear()
        for (row in ERROR_VIEW_HEIGHT until map.getY() + ERROR_VIEW_HEIGHT) {
            for (column in 0 until map.getX()) {
                val cell = map.field[row - ERROR_VIEW_HEIGHT][column]
                if (cell.item != null) {
                    screen.setCharacter(
                        column, row,
                        when (cell.item) {
                            is Shield -> TextCharacter.fromCharacter(SHIELD_CHAR)[0]
                            is Sword -> TextCharacter.fromCharacter(SWORD_CHAR)[0]
                            is Apple -> TextCharacter.fromCharacter(APPLE_CHAR)[0]
                            else -> { TextCharacter.fromCharacter(NON_WALKABLE_CHAR)[0] }
                        }
                    )
                } else if (cell.enemy != null) {
                    screen.setCharacter(
                        column, row,
                        TextCharacter.fromCharacter(cell.enemy.toString()[0])[0].withBackgroundColor(
                            when (cell.enemy!!.style) {
                                EnemyStyle.CYBORG -> TextColor.RGB(80, 200, 0)
                                EnemyStyle.DRAGON -> TextColor.RGB(0, 80, 200)
                                EnemyStyle.SKELETON -> TextColor.RGB(200, 0, 80)
                            }
                        )
                    )
                } else {
                    screen.setCharacter(
                        column, row,
                        when (cell.cellType) {
                            CellType.WALKABLE -> TextCharacter.fromCharacter(WALKABLE_CHAR)[0]
                            CellType.NON_WALKABLE -> TextCharacter.fromCharacter(NON_WALKABLE_CHAR)[0]
                        }
                    )
                }
            }
        }
        characterView.type = "MAP"
        characterView.draw()
        errorView.draw()
        screen.refresh()
    }
}
