package ru.roguelike.view

import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.screen.Screen
import ru.roguelike.model.Hero
import ru.roguelike.util.Constants
import ru.roguelike.util.Constants.EXP_FOR_LEVEL_UP

/**
 * This class provides one method which draws character
 */
class CharacterView(
    private val character: Hero,
    private val screen: Screen
) : Drawable {
    var type = "MAP"

    /**
     * This method draws the character.
     */
    override fun draw() {
        if (type == "MAP") {
            screen.setCharacter(
                character.coordinates.x, character.coordinates.y + Constants.ERROR_VIEW_HEIGHT,
                TextCharacter.fromCharacter(CHARACTER_CHAR)[0].withForegroundColor(TextColor.RGB(255, 0, 0))
            )
        }
        val exp = "EXPERIENCE" + " " + character.exp.toString() + "/" + EXP_FOR_LEVEL_UP
        for (column in exp.indices) {
            for (row in screen.terminalSize.rows - 4 until screen.terminalSize.rows - 3) {
                screen.setCharacter(
                    column, row,
                    TextCharacter.fromCharacter(exp[column])[0]
                )
            }
        }
        val armor = "ARMOR" + " " + character.armor.toString()
        for (column in armor.indices) {
            for (row in screen.terminalSize.rows - 3 until screen.terminalSize.rows - 2) {
                screen.setCharacter(
                    column, row,
                    TextCharacter.fromCharacter(armor[column])[0]
                )
            }
        }
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
