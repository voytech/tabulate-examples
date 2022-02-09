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
import io.github.voytech.tabulatexamples.invoice.sections.issuerSection
import io.github.voytech.tabulatexamples.invoice.sections.shippingDetailsSection
import io.github.voytech.tabulatexamples.invoice.sections.titleSection
import java.math.BigDecimal
import java.time.LocalDate

fun RowsBuilderApi<InvoiceLineItem>.invoiceItemsHeaderRow() {
    newRow {
        textCell { "DESCRIPTION" }
        textCell { "QTY" }
        textCell { "UNIT PRICE" }
        textCell { "VAT" }
        textCell { "TOTAL" }
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

class InvoiceDetailsRowsBuilder {
    lateinit var invoiceNumber: String
    lateinit var issueDate: LocalDate
    lateinit var dueDate: LocalDate
    lateinit var issuer: CompanyAddress
    lateinit var client: CompanyAddress
}

fun RowsBuilderApi<InvoiceLineItem>.invoiceShippingDetailsRow(block: InvoiceDetailsRowsBuilder.() -> Unit) {
    with(InvoiceDetailsRowsBuilder().apply(block)) {
        newRow {
            textCell { "BILL TO" }
            textCell { "SHIP TO" }
            emptyCell()
            textCell { "Invoice No:" }
            textCell { invoiceNumber }
            attributes {
                alignment { horizontal = DefaultHorizontalAlignment.CENTER }
                borders {
                    bottomBorderColor = Colors.BLACK
                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                }
            }
        }
        matching { records() } assign {
            cell { attributes { text { weight = DefaultWeightStyle.BOLD }} }
        }
        newRow {
            textCell { issuer.companyName }
            textCell { client.companyName }
            emptyCell()
            textCell { "Invoice Date:"}
            dateCell{ issueDate }
        }
        newRow {
            textCell { issuer.contactName }
            textCell { client.contactName }
            emptyCell()
            textCell { "Due Date:"}
            dateCell{ dueDate }
        }
        newRow {
            textCell { issuer.address }
            textCell { client.address }
        }
        newRow {
            textCell { issuer.phone }
            textCell { client.phone }
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
    issueDate: LocalDate = LocalDate.now(),
    dueDate: LocalDate = LocalDate.now(),
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
            titleSection()
            issuerSection {
                rowIndex = 1
                issuer = issuerDetails
                imageUrl = "src/main/resources/logo.png"
            }
            separatorRow()
            shippingDetailsSection {
                addressTitle = "BILL TO"
                rowIndex = 10
                columnIndex = 0
                address = issuerDetails
            }
            shippingDetailsSection {
                addressTitle = "SHIP TO"
                rowIndex = 10
                columnIndex = 1
                address = clientDetails
            }
            /*invoiceShippingDetailsRow {
                this.invoiceNumber = invoiceNumber
                this.issuer = issuerDetails
                this.client = clientDetails
                this.issueDate = issueDate
                this.dueDate = dueDate
            }*/
            separatorRow()
            invoiceItemsHeaderRow()
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