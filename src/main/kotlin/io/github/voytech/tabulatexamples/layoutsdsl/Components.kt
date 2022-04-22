package io.github.voytech.tabulatexamples.layoutsdsl

import io.github.voytech.tabulate.model.attributes.Colors
import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.borders
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultTypeHints
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.contract.CellType
import io.github.voytech.tabulate.model.attributes.cell.text
import java.time.LocalDate
import java.time.LocalDateTime

fun <T> SectionBuilderApi<T>.separator(rowCount: Int, span: Int = 1) {
    repeat((1..rowCount).count()) {
        newRow {
            cell {
                colSpan = span
                value = ""
            }
        }
    }
}

fun <T> SectionBuilderApi<T>.heading(title: String, span: Int = 1) {
    newRow {
        cell {
            value = title
            colSpan = span
        }
        attributes {
            alignment { horizontal = DefaultHorizontalAlignment.CENTER }
            text { weight = DefaultWeightStyle.BOLD }
            borders {
                bottomBorderColor = Colors.BLACK
                bottomBorderStyle = DefaultBorderStyle.DOUBLE
            }
        }
    }
}

fun <T, V : Any> SectionBuilderApi<T>.field(
    column: Int = 0,
    fieldName: String? = null,
    fieldValue: V
) {
    newRow {
        fieldName?.let {
            cell(column) {
                value = fieldName
                attributes {
                    text { weight = DefaultWeightStyle.BOLD }
                }
            }
        }
        cell(column + (fieldName?.let { 1 } ?: 0)) {
            value = fieldValue
            typeHint { resolveType(fieldValue) }
            attributes {
                alignment {
                    horizontal = fieldName?.let {
                        DefaultHorizontalAlignment.RIGHT
                    } ?:  DefaultHorizontalAlignment.LEFT
                }
            }
        }
    }
}

private fun <V : Any> resolveType(value: V): CellType =
    when (value) {
        is String -> DefaultTypeHints.STRING
        is Number -> DefaultTypeHints.NUMERIC
        is LocalDate -> DefaultTypeHints.DATE
        is LocalDateTime -> DefaultTypeHints.DATE
        else -> DefaultTypeHints.STRING
    }