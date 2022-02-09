package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.*
import io.github.voytech.tabulate.excel.model.attributes.dataFormat
import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.*
import io.github.voytech.tabulate.model.attributes.cell.enums.contract.CellType
import io.github.voytech.tabulate.model.attributes.row.height
import java.math.BigDecimal
import java.time.LocalDate

fun <T> RowsBuilderApi<T>.separatorRow(height: Int? = null) {
    newRow {
        height?.let {
            attributes {
                height { px = it }
            }
        }
    }
}

fun <T> RowsBuilderApi<T>.separatorRows(count: Int = 1) {
    (1..count).forEach { _ -> separatorRow() }
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

private fun <T, R> cellBuilderBlock(
    cSpan: Int = 1,
    rSpan: Int = 1,
    cType: CellType,
    valueSupplier: () -> R
): CellBuilderApi<T>.() -> Unit = {
    colSpan = cSpan
    rowSpan = rSpan
    value = valueSupplier()
    typeHint { cType }
}

fun <T> RowBuilderApi<T>.textCell(index: Int? = null, colSpan: Int = 1, rowSpan: Int = 1, valueSupplier: () -> String) {
    val block = cellBuilderBlock<T,String>(colSpan, rowSpan, DefaultTypeHints.STRING, valueSupplier)
    index?.let { cell(it, block) } ?: cell(block)
}

fun <T> RowBuilderApi<T>.decimalCell(
    index: Int? = null,
    colSpan: Int = 1,
    rowSpan: Int = 1,
    valueSupplier: () -> BigDecimal,
) {
    val block = cellBuilderBlock<T,BigDecimal>(colSpan, rowSpan, DefaultTypeHints.NUMERIC, valueSupplier)
    index?.let { cell(it, block) } ?: cell(block)
}

fun <T> RowBuilderApi<T>.dateCell(index: Int? = null, dateFormat: String = "yyyy-mm-dd", supplier: () -> LocalDate) {
    val block = cellBuilderBlock<T, LocalDate>(1,1, DefaultTypeHints.DATE, supplier)
    index?.let {
        cell(it, block.also { attributes {  dataFormat { value = dateFormat }  } }) } ?:
        cell(block.also { attributes {  dataFormat { value = dateFormat }  }  })
}

fun <T> RowsBuilderApi<T>.oneCellRow(block: CellBuilderApi<T>.() -> Unit) {
    newRow {
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

private fun buildAllBorders(block: CellAllBordersAttribute.() -> Unit): CellBordersAttribute.Builder {
    return CellAllBordersAttribute().apply(block).let {
        CellBordersAttribute.Builder().apply {
            leftBorderStyle = it.style
            leftBorderColor = it.color
            rightBorderColor = it.color
            rightBorderStyle = it.style
            topBorderColor = it.color
            topBorderStyle = it.style
            bottomBorderColor = it.color
            bottomBorderStyle = it.style
        }
    }
}

fun <T> CellLevelAttributesBuilderApi<T>.allBorders(block: CellAllBordersAttribute.() -> Unit) {
    attribute(buildAllBorders(block))
}

fun <T> RowLevelAttributesBuilderApi<T>.allBorders(block: CellAllBordersAttribute.() -> Unit) {
    attribute(buildAllBorders(block))
}