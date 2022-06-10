package ru.roguelike.model.generator

import ru.roguelike.model.EnemyFactory
import ru.roguelike.model.Field
import ru.roguelike.model.generator.bsp.BSPFieldGenerator
import ru.roguelike.util.Constants
import java.util.Properties

/**
 * Specify format of file with field
 */
enum class FormatType {
    JSON,
    PLAIN
}

/**
 * Builder for field
 */
class FieldBuilder(private val factory: EnemyFactory) {
    private var width = Constants.FIELD_WIDTH
    private var height = Constants.FIELD_HEIGHT
    private var filePath: String? = null
    private var fileFormatType: FormatType = FormatType.JSON

    /**
     * Creates field with parameters, which were specified earlier
     */
    fun build(): Field {
        Constants.FIELD_HEIGHT = height
        Constants.FIELD_WIDTH = width
        return if (filePath == null) {
            BSPFieldGenerator(factory).generate()
        } else {
            filePath?.let {
                when (fileFormatType) {
                    FormatType.JSON -> JsonFileFieldGenerator(it).generate()
                    FormatType.PLAIN -> TextFileFieldGenerator(it).generate()
                }
            } ?: throw IllegalStateException("File path is not specified")
        }
    }

    /**
     * Specify width of field
     */
    fun withWidth(width: Int): FieldBuilder {
        this.width = width
        return this
    }

    /**
     * Specify height of field
     */
    fun withHeight(height: Int): FieldBuilder {
        this.height = height
        return this
    }

    /**
     * Specify path to file, from which we want to read our field
     */
    fun fromFile(path: String): FieldBuilder {
        filePath = path
        return this
    }

    /**
     * Specify format of file, from which we want to read our field
     */
    fun withFormat(formatType: FormatType): FieldBuilder {
        fileFormatType = formatType
        return this
    }

    /**
     * Specify parameters from properties
     */
    fun withProperties(prop: Properties): FieldBuilder {
        prop.getProperty("width")?.toInt()?.let { withWidth(it) }
        prop.getProperty("height")?.toInt()?.let { withHeight(it) }
        prop.getProperty("format")?.let {
            withFormat(FormatType.valueOf(it))
        }
        prop.getProperty("path")?.let { fromFile(it) }

        return this
    }
}
