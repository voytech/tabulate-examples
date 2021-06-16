package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.*
import io.github.voytech.tabulate.excel.model.attributes.dataFormat
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.borders
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultVerticalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.cell.text
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

fun <T> CellsBuilderApi<T>.separatorCell(span: Int = 1) {
    cell {
        value = ""
        colSpan = span
    }
}

fun <T> RowBuilderApi<T>.separatorCell(span: Int = 1) {
    cells { separatorCell(span) }
}

fun <T> RowBuilderApi<T>.dateCell(date: LocalDate, dateFormat: String = "yyyy-mm-dd" ) {
    cells {
        cell {
            value = date
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

fun <T> CellBuilderApi<T>.doubleUnderlineBoldStyle() {
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
