package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultTypeHints
import io.github.voytech.tabulatexamples.invoice.CompanyAddress
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder
import io.github.voytech.tabulatexamples.layoutsdsl.separator

class IssuerRowBuilder {
    lateinit var imageUrl: String
    lateinit var issuer: CompanyAddress
}

fun SectionsBuilder<InvoiceLineItem>.issuerSection(block: IssuerRowBuilder.() -> Unit) {
    section {
        with(IssuerRowBuilder().apply(block)) {
            newRow {
                cell {
                    colSpan = 3
                    value = issuer.companyName
                }
                cell {
                    colSpan = 2
                    rowSpan = 8
                    value = imageUrl
                    typeHint { DefaultTypeHints.IMAGE_URL }
                }
            }
            newRow {
                cell {
                    colSpan = 3
                    value = issuer.address
                }
            }
            newRow {
                cell {
                    colSpan = 3
                    value = issuer.address2
                }
            }
            newRow {
                cell {
                    colSpan = 3
                    value = issuer.phone
                }
            }
            separator(4,3)
        }
    }
}