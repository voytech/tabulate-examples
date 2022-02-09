package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.borders
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulatexamples.invoice.CompanyAddress
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.textCell

class ShippingRowsBuilder {
    var rowIndex: Int = 0
    var columnIndex: Int = 0
    lateinit var addressTitle: String
    lateinit var address: CompanyAddress
}

fun RowsBuilderApi<InvoiceLineItem>.shippingDetailsSection(block: ShippingRowsBuilder.() -> Unit) {
    ShippingRowsBuilder().apply(block).let {
        newRow(it.rowIndex) {
            textCell(index = it.columnIndex) { it.addressTitle }
            attributes {
                alignment { horizontal = DefaultHorizontalAlignment.CENTER }
                borders {
                    bottomBorderColor = Colors.BLACK
                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                }
            }
        }
        newRow {
            textCell(index = it.columnIndex) { it.address.companyName }
        }
        newRow {
            textCell(index = it.columnIndex) { it.address.contactName }
        }
        newRow {
            textCell(index = it.columnIndex) { it.address.address }
        }
        newRow {
            textCell(index = it.columnIndex) { it.address.phone }
        }
    }
}
