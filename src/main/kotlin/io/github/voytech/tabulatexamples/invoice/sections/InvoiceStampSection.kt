package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulate.excel.model.ExcelTypeHints
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.alignment
import io.github.voytech.tabulate.model.attributes.cell.borders
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder
import java.time.LocalDate

class InvoiceStampBuilder {
    lateinit var number: String
    lateinit var dueDate: LocalDate
    lateinit var issueDate: LocalDate
}

fun SectionsBuilder<InvoiceLineItem>.invoiceStampSection(block: InvoiceStampBuilder.() -> Unit) {
    section {
        InvoiceStampBuilder().apply(block).let {
            newRow {
                cell {
                    value = "Invoice:"
                    colSpan = 3
                }
                attributes {
                    alignment { horizontal = DefaultHorizontalAlignment.CENTER }
                    borders {
                        bottomBorderColor = Colors.BLACK
                        bottomBorderStyle = DefaultBorderStyle.DOUBLE
                    }
                }
            }
            newRow {
                cell {
                    value = it.number
                    colSpan = 3
                    attributes {
                        alignment { horizontal = DefaultHorizontalAlignment.RIGHT }
                    }
                }
            }
            newRow {
                cell {
                    value = it.issueDate.toString()
                    typeHint { ExcelTypeHints.DATE }
                    colSpan = 3
                    attributes {
                        alignment { horizontal = DefaultHorizontalAlignment.RIGHT }
                    }
                }
            }
            newRow {
                cell {
                    value = it.dueDate
                    colSpan = 3
                    attributes {
                        alignment { horizontal = DefaultHorizontalAlignment.RIGHT }
                    }
                }
            }
        }
    }
}
