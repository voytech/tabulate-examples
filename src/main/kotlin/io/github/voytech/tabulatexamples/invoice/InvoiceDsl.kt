package io.github.voytech.tabulatexamples.invoice

import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.*
import io.github.voytech.tabulate.model.attributes.column.columnWidth
import io.github.voytech.tabulate.template.context.AdditionalSteps
import io.github.voytech.tabulate.template.tabulate
import io.github.voytech.tabulatexamples.*
import io.github.voytech.tabulatexamples.invoice.sections.invoiceDetailsSection
import io.github.voytech.tabulatexamples.invoice.sections.issuerSection
import io.github.voytech.tabulatexamples.invoice.sections.addressDetailsSection
import io.github.voytech.tabulatexamples.invoice.sections.titleSection
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder
import io.github.voytech.tabulatexamples.layoutsdsl.field
import io.github.voytech.tabulatexamples.layoutsdsl.layout
import io.github.voytech.tabulatexamples.layoutsdsl.separator
import java.math.BigDecimal
import java.time.LocalDate

fun SectionsBuilder<InvoiceLineItem>.lineItemsHeaderSection() {
    section {
        newRow {
            cell {  value = "DESCRIPTION" }
            cell {  value = "QTY" }
            cell {  value = "UNIT PRICE" }
            cell {  value = "VAT" }
            cell {  value = "TOTAL" }
            attributes {
                allBorders {
                    style = DefaultBorderStyle.SOLID
                }
                text {
                    fontColor = Colors.WHITE
                    weight = DefaultWeightStyle.BOLD
                }
                background {
                    color = Colors.BLACK // add more colors
                    fill = DefaultCellFill.SOLID
                }
            }
        }
    }
}

class InvoiceSummaryRowsBuilder {
    lateinit var subtotal: BigDecimal
    lateinit var discounts: BigDecimal
    lateinit var taxes: BigDecimal
    lateinit var total: BigDecimal
}

fun SectionsBuilder<InvoiceLineItem>.invoiceSummarySection(
    column: Int = 0,
    block: InvoiceSummaryRowsBuilder.() -> Unit
) {
    section {
        with(InvoiceSummaryRowsBuilder().apply(block)) {
            field(column,"Subtotal", subtotal)
            field(column,"Discounts", discounts)
            field(column,"Taxes", taxes)
            field(column,"Total", total)
        }
    }
}

fun SectionsBuilder<InvoiceLineItem>.thankYou(span: Int = 0) {
    section {
        newRow {
            cell {
                value =  "Thank You for your business!"
                colSpan = span
                attributes {
                    alignment { horizontal = DefaultHorizontalAlignment.RIGHT }
                    text { weight = DefaultWeightStyle.BOLD }
                }
            }
        }
    }
}

fun Iterable<InvoiceLineItem>.printInvoice(
    fileName: String,
    issuerDetails: CompanyAddress,
    clientDetails: CompanyAddress,
    invoiceNumber: String = "#00001",
    invoiceIssueDate: LocalDate = LocalDate.now(),
    invoiceDueDate: LocalDate = LocalDate.now(),
) {
    val items = this
    tabulate(fileName) {
        attributes { columnWidth { auto = true } }
        columns {
            column(InvoiceLineItem::description)
            column(InvoiceLineItem::qty)
            column(InvoiceLineItem::unitPrice)
            column(InvoiceLineItem::vat)
            column(InvoiceLineItem::total)
        }
        rows {
            layout {
                horizontal { titleSection() }
                horizontal {
                    issuerSection {
                        issuer = issuerDetails
                        imageUrl = "src/main/resources/logo.png"
                    }
                }
                horizontal {
                    section { separator(1,5) }
                }
                horizontal {
                    addressDetailsSection {
                        addressTitle = "BILL TO"
                        address = issuerDetails
                    }
                    addressDetailsSection {
                        addressTitle = "SHIP TO"
                        address = clientDetails
                    }
                    invoiceDetailsSection {
                        number = invoiceNumber
                        issueDate = invoiceIssueDate
                        dueDate = invoiceDueDate
                    }
                }
                horizontal {
                    section { separator(1,5) }
                }
                horizontal {
                    lineItemsHeaderSection()
                }
                horizontal(AdditionalSteps.TRAILING_ROWS) {
                    section { separator(1,5) }
                }
                horizontal(AdditionalSteps.TRAILING_ROWS) {
                    invoiceSummarySection(column = 3) {
                        subtotal = items.sumOf { it.unitPrice.multiply(it.qty.toBigDecimal()) }
                        discounts = BigDecimal.ZERO
                        taxes = items.sumOf { it.vat.multiply(it.unitPrice.multiply(it.qty.toBigDecimal())) }
                        total = items.sumOf { it.total }
                    }
                }
                horizontal(AdditionalSteps.TRAILING_ROWS) {
                    section { separator(1,5) }
                }
                horizontal(AdditionalSteps.TRAILING_ROWS) {
                    thankYou(span = 5)
                }
            }
        }
    }
}