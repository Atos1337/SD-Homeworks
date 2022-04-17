package ru.roguelike.view

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.screen.Screen
import ru.roguelike.model.Character

/**
 * This class provides one method which draws character
 */
class CharacterView(
    private val character: Character,
    private val screen: Screen
) : Drawable {
    /**
     * This method draws the character.
     */
    override fun draw() {
        screen.setCharacter(
            character.coordinates.x, character.coordinates.y,
            TextCharacter.fromCharacter(CHARACTER_CHAR)[0]
        )
        val health = "HEALTH" + " " + character.hp.toString() + "/" + character.maxHp.toString()
        for (column in health.indices) {
            for (row in screen.terminalSize.rows - 2 until screen.terminalSize.rows - 1) {
                screen.setCharacter(
                    column, row,
                    TextCharacter.fromCharacter(health[column])[0]
                )
            }
        }
        val damage = "DAMAGE" + " " + character.damage.toString()
        for (column in damage.indices) {
            for (row in screen.terminalSize.rows - 1 until screen.terminalSize.rows) {
                screen.setCharacter(
                    column, row,
                    TextCharacter.fromCharacter(damage[column])[0]
                )
            }
        }
    }
}
