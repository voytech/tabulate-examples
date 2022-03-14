package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.borders
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulatexamples.invoice.CompanyAddress
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder

class ShippingRowsBuilder {
    lateinit var addressTitle: String
    lateinit var address: CompanyAddress
}

fun SectionsBuilder<InvoiceLineItem>.shippingDetailsSection(block: ShippingRowsBuilder.() -> Unit) {
    section {
        ShippingRowsBuilder().apply(block).let {
            newRow {
                cell { value = it.addressTitle }
                attributes {
                    alignment { horizontal = DefaultHorizontalAlignment.CENTER }
                    borders {
                        bottomBorderColor = Colors.BLACK
                        bottomBorderStyle = DefaultBorderStyle.DOUBLE
                    }
                }
            }
            newRow {
                cell { value =  it.address.companyName }
            }
            newRow {
                cell { value = it.address.contactName }
            }
            newRow {
                cell { value = it.address.address }
            }
            newRow {
                cell { value = it.address.phone }
            }
        }
    }
}
