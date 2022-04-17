package ru.roguelike.model

/**
 * Class that store instrutions information
 */
class InstructionModel(
    val instructions: Map<String, String>,
    val description: String,
    val authors: List<String>
)
