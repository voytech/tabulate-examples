package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.cell.text
import io.github.voytech.tabulate.model.attributes.row.height
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder


fun SectionsBuilder<InvoiceLineItem>.titleSection(
    invoiceLabel: String = "INVOICE",
    height: Int = 160,
) {
    section {
        newRow {
            attributes { height { px = height } }
            cell {
                colSpan = 5
                value = invoiceLabel
                attributes {
                    text {
                        weight = DefaultWeightStyle.BOLD
                    }
                    alignment {
                        horizontal = DefaultHorizontalAlignment.CENTER
                    }
                }
            }
        }
    }
}