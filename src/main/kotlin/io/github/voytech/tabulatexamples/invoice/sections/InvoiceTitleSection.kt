package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.row.height
import io.github.voytech.tabulatexamples.boldText
import io.github.voytech.tabulatexamples.horizontallyAligned
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem


fun RowsBuilderApi<InvoiceLineItem>.titleSection(
    rowIndex: Int = 0,
    invoiceLabel: String = "INVOICE",
    height: Int = 160,
) {
    newRow(rowIndex) {
        attributes { height { px = height } }
        cell {
            colSpan = 5
            value = invoiceLabel
            boldText()
            horizontallyAligned(align = DefaultHorizontalAlignment.CENTER)
        }
    }
}