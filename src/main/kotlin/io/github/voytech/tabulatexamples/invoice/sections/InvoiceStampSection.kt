package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.borders
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulatexamples.dateCell
import io.github.voytech.tabulatexamples.invoice.CompanyAddress
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.textCell
import java.time.LocalDate

class InvoiceStampBuilder {
    var rowIndex: Int = 0
    var columnIndex: Int = 0
    lateinit var number: String
    lateinit var dueDate: LocalDate
    lateinit var issueDate: LocalDate
}

fun RowsBuilderApi<InvoiceLineItem>.invoiceStampSection(block: InvoiceStampBuilder.() -> Unit) {
    InvoiceStampBuilder().apply(block).let {
        newRow(it.rowIndex) {
            textCell(index = it.columnIndex) { "Invoice:" }
            attributes {
                alignment { horizontal = DefaultHorizontalAlignment.CENTER }
                borders {
                    bottomBorderColor = Colors.BLACK
                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                }
            }
        }
        newRow(it.rowIndex + 1) {
            textCell(index = it.columnIndex) { it.number }
        }
        newRow(it.rowIndex + 2) {
            textCell(index = it.columnIndex) { it.issueDate.toString() }
        }
        newRow(it.rowIndex + 3) {
            dateCell(index = it.columnIndex) { it.dueDate }
        }
    }
}
