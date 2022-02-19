package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultTypeHints
import io.github.voytech.tabulatexamples.invoice.CompanyAddress
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.separatorRows
import io.github.voytech.tabulatexamples.textCell

class IssuerRowBuilder {
    var rowIndex: Int = 0
    lateinit var imageUrl: String
    lateinit var issuer: CompanyAddress
}

fun RowsBuilderApi<InvoiceLineItem>.issuerSection(block: IssuerRowBuilder.() -> Unit) {
    with(IssuerRowBuilder().apply(block)) {
        newRow(rowIndex) {
            textCell(colSpan = 3) { issuer.companyName }
            cell {
                colSpan = 2
                rowSpan = 8
                value = imageUrl
                typeHint { DefaultTypeHints.IMAGE_URL }
            }
        }
        newRow(rowIndex+1) {
            textCell(colSpan = 3) { issuer.address }
        }
        newRow(rowIndex+2) {
            textCell(colSpan = 3) { issuer.address2 }
        }
        newRow(rowIndex+3) {
            textCell(colSpan = 3) { issuer.phone }
        }
        separatorRows(rowIndex + 4,4)
    }
}