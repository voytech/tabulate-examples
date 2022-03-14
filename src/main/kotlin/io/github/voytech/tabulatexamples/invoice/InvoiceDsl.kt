package io.github.voytech.tabulatexamples.invoice

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.api.builder.dsl.newTrailingRow
import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultCellFill
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.column.columnWidth
import io.github.voytech.tabulate.template.tabulate
import io.github.voytech.tabulatexamples.*
import io.github.voytech.tabulatexamples.invoice.sections.invoiceStampSection
import io.github.voytech.tabulatexamples.invoice.sections.issuerSection
import io.github.voytech.tabulatexamples.invoice.sections.shippingDetailsSection
import io.github.voytech.tabulatexamples.invoice.sections.titleSection
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder
import io.github.voytech.tabulatexamples.layoutsdsl.layout
import java.math.BigDecimal
import java.time.LocalDate

fun SectionsBuilder<InvoiceLineItem>.invoiceItemsHeaderRow() {
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

//TODO section styles (section borders)
fun RowsBuilderApi<InvoiceLineItem>.invoiceSummaryRow(
    trailingRowStartIndex: Int = 0,
    firstColumnIndex: Int = 3,
    block: InvoiceSummaryRowsBuilder.() -> Unit
) {
    with(InvoiceSummaryRowsBuilder().apply(block)) {
        newTrailingRow(trailingRowStartIndex) {
            textCell(firstColumnIndex) { "Subtotal" }
            decimalCell { subtotal }
        }
        newTrailingRow {
            textCell(firstColumnIndex) { "Discounts" }
            decimalCell { discounts }
        }
        newTrailingRow {
            textCell(firstColumnIndex) { "Taxes" }
            decimalCell { taxes }
        }
        newTrailingRow {
            textCell(firstColumnIndex) { "Total" }
            decimalCell { total }
        }
    }
}

fun RowsBuilderApi<InvoiceLineItem>.invoiceTermsAndInstructions(trailingRowIndex: Int = 0, firstColumnIndex: Int = 0) {
    newTrailingRow(trailingRowIndex) {
        textCell(firstColumnIndex) { "Thank You for your business!" }
    }
    newTrailingRow {
        textCell(firstColumnIndex) { "Terms & Instructions" }
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
                    shippingDetailsSection {
                        addressTitle = "BILL TO"
                        address = issuerDetails
                    }
                    shippingDetailsSection {
                        addressTitle = "SHIP TO"
                        address = clientDetails
                    }
                    invoiceStampSection {
                        number = invoiceNumber
                        issueDate = invoiceIssueDate
                        dueDate = invoiceDueDate
                    }
                }
                horizontal {
                    invoiceItemsHeaderRow()
                }
            }
            // footer {  } // TODO : BUG - when I skip footer which is 0 index trailing row - rendering of remaining rows stops as there is a gap when next trailing row starts at 1.
            invoiceSummaryRow {
                subtotal = items.sumOf { it.unitPrice.multiply(it.qty.toBigDecimal()) }
                discounts = BigDecimal.ZERO
                taxes = items.sumOf { it.vat.multiply(it.unitPrice.multiply(it.qty.toBigDecimal())) }
                total = items.sumOf { it.total }
            }
            invoiceTermsAndInstructions()
        }
    }
}