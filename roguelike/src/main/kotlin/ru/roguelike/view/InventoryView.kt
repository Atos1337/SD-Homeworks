package ru.roguelike.view

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.screen.Screen
import ru.roguelike.model.Apple
import ru.roguelike.model.InventoryModel
import ru.roguelike.model.Shield
import ru.roguelike.model.Sword
import ru.roguelike.util.Constants.ERROR_VIEW_HEIGHT

/**
 * This class provides one method which draws inventory.
 */
class InventoryView(
    private val inventory: InventoryModel,
    private val characterView: CharacterView,
    private val screen: Screen
) : Drawable {
    private val errorView = ErrorView(screen)
    override fun setError(message: String) {
        errorView.message = message
    }
    /**
     * This method draws inventory.
     */
    override fun draw() {
        screen.clear()
        var currentIndexInItems = false
        for ((index, item) in inventory.items.withIndex()) {
            var currentCharacter = when (item) {
                is Shield -> TextCharacter.fromCharacter(SHIELD_CHAR)[0]
                is Sword -> TextCharacter.fromCharacter(SWORD_CHAR)[0]
                is Apple -> TextCharacter.fromCharacter(APPLE_CHAR)[0]
                else -> { TextCharacter.fromCharacter(NON_WALKABLE_CHAR)[0] }
            }
            if (index in inventory.equippedItems) {
                currentCharacter = currentCharacter.withBackgroundColor(TextColor.RGB(0, 255, 0))
            }
            if (index == inventory.currentItemIndex) {
                currentIndexInItems = true
                currentCharacter = currentCharacter.withBackgroundColor(TextColor.RGB(0, 0, 255))
            }
            screen.setCharacter(
                0, index + ERROR_VIEW_HEIGHT,
                currentCharacter
            )
            for ((characterIndex, character) in item.toString().withIndex()) {
                screen.setCharacter(
                    characterIndex + 2, index + ERROR_VIEW_HEIGHT,
                    TextCharacter.fromCharacter(character)[0]
                )
            }
        }
        if (!currentIndexInItems) {
            screen.setCharacter(
                0, inventory.currentItemIndex + ERROR_VIEW_HEIGHT,
                TextCharacter.fromCharacter(' ')[0].withBackgroundColor(TextColor.RGB(0, 0, 255))
            )
        }
        characterView.type = "INVENTORY"
        characterView.draw()
        errorView.draw()
        screen.refresh()
    }
}
