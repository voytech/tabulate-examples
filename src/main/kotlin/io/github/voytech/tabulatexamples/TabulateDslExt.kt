package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.*
import io.github.voytech.tabulate.excel.model.attributes.dataFormat
import io.github.voytech.tabulate.model.CellType
import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultVerticalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.row.height
import java.time.LocalDate


fun <T> RowsBuilderApi<T>.separatorRow(height: Int? = null) {
    row {
        height?.let {
            attributes {
                height { px = it }
            }
        }
    }
}

fun <T> CellsBuilderApi<T>.emptyCell(span: Int = 1) {
    cell {
        value = ""
        colSpan = span
    }
}

fun <T> RowBuilderApi<T>.emptyCell(colSpan: Int = 1) {
    cells { emptyCell(colSpan) }
}

fun <T> RowBuilderApi<T>.textCell(colSpan: Int = 1,rowSpan: Int = 1,valueSupplier: () -> String) {
    cell {
        this.colSpan = colSpan
        this.rowSpan = rowSpan
        value = valueSupplier()
        type = CellType.STRING
    }
}

fun <T> RowBuilderApi<T>.dateCell(dateFormat: String = "yyyy-mm-dd",supplier: () -> LocalDate) {
    cells {
        cell {
            value = supplier()
            attributes {
                dataFormat { value = dateFormat }
            }
        }
    }
}

fun <T> RowsBuilderApi<T>.oneCellRow(block: CellBuilderApi<T>.() -> Unit) {
    row {
        cell(block)
    }
}

fun <T> CellBuilderApi<T>.boldText(size: Int = 14) {
    attributes {
        text {
            weight = DefaultWeightStyle.BOLD
            fontSize = size
        }
    }
}

fun <T> CellBuilderApi<T>.doubleUnderline() {
    attributes {
        text { weight = DefaultWeightStyle.BOLD }
        borders {
            bottomBorderColor = Colors.BLACK
            bottomBorderStyle = DefaultBorderStyle.DOUBLE
        }
    }
}

fun <T> CellBuilderApi<T>.horizontallyAligned(align: DefaultHorizontalAlignment = DefaultHorizontalAlignment.LEFT) {
    attributes {
        alignment {
            vertical = DefaultVerticalAlignment.MIDDLE
            horizontal = align
        }
    }
}

class CellAllBordersAttribute {
    lateinit var style: DefaultBorderStyle
    var color: Color = Colors.BLACK
}

private fun buildAllBorders(block: CellAllBordersAttribute.() -> Unit): CellBordersAttribute {
    return CellAllBordersAttribute().apply(block).let {
        CellBordersAttribute(
            leftBorderStyle = it.style,
            leftBorderColor = it.color,
            rightBorderColor = it.color,
            rightBorderStyle = it.style,
            topBorderColor = it.color,
            topBorderStyle = it.style,
            bottomBorderColor = it.color,
            bottomBorderStyle = it.style
        )
    }
}

fun <T> CellLevelAttributesBuilderApi<T>.allBorders(block: CellAllBordersAttribute.() -> Unit) {
    attribute(buildAllBorders(block))
}

fun <T> RowLevelAttributesBuilderApi<T>.allBorders(block: CellAllBordersAttribute.() -> Unit) {
    attribute(buildAllBorders(block))
}