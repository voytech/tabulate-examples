package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.api.builder.dsl.cell
import io.github.voytech.tabulate.api.builder.dsl.footer
import io.github.voytech.tabulate.api.builder.dsl.trailingRow
import io.github.voytech.tabulate.model.CellType
import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultCellFill
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.row.height
import io.github.voytech.tabulate.template.tabulate
import java.math.BigDecimal
import java.time.LocalDate


fun RowsBuilderApi<InvoiceLineItem>.invoiceHeaderRow() {
    val invoiceLabel = "INVOICE"
    row {
        attributes { height { px = 160 } }
        cell {
            colSpan = 5
            value = invoiceLabel
            boldText()
            horizontallyAligned(align = DefaultHorizontalAlignment.CENTER)
        }
    }
}

class CompanyHeaderRowBuilder {
    lateinit var imageUrl: String
    lateinit var issuer: CompanyAddress
}

fun RowsBuilderApi<InvoiceLineItem>.companyHeaderRows(block: CompanyHeaderRowBuilder.() -> Unit) {
    with(CompanyHeaderRowBuilder().apply(block)) {
        row {
            textCell(colSpan = 3) { issuer.companyName }
            cell {
                colSpan = 2
                rowSpan = 8
                value = imageUrl
                type = CellType.IMAGE_URL
                attributes {
                    allBorders {
                        color = Colors.BLACK
                        style = DefaultBorderStyle.DOUBLE
                    }
                }
            }
        }
        row {
            textCell(colSpan = 3) { issuer.address }
        }
        row {
            textCell(colSpan = 3) { issuer.address2 }
        }
        row {
            textCell(colSpan = 3) { issuer.phone }
        }
        separatorRows(4)
        /* - TODO does not work
        row {
            cell {
                value = ""
                rowSpan = 4
                colSpan = 3
            }
        }
         */
    }
}

fun RowsBuilderApi<InvoiceLineItem>.invoiceItemsHeaderRow() {
    row {
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
        row {
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
        row {
            matching { it.hasRecord() }
            cell { attributes { text { weight = DefaultWeightStyle.BOLD }} }
        }
        row {
            textCell { issuer.companyName }
            textCell { client.companyName }
            emptyCell()
            textCell { "Invoice Date:"}
            dateCell{ issueDate }
        }
        row {
            textCell { issuer.contactName }
            textCell { client.contactName }
            emptyCell()
            textCell { "Due Date:"}
            dateCell{ dueDate }
        }
        row {
            textCell { issuer.address }
            textCell { client.address }
        }
        row {
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
fun RowsBuilderApi<InvoiceLineItem>.invoiceSummaryRow(trailingRowIndex: Int = 0, block: InvoiceSummaryRowsBuilder.() -> Unit) {
    with(InvoiceSummaryRowsBuilder().apply(block)) {
        trailingRow(trailingRowIndex) {
            textCell(3) { "Subtotal" } // TODO! addressing cells by index!
            decimalCell(4) { subtotal }
        }
        trailingRow {
            textCell(3) { "Discounts" }
            decimalCell(4) { discounts }
        }
        trailingRow {
            textCell(3) { "Taxes" }
            decimalCell(4) { taxes }
        }
        trailingRow {
            textCell(3) { "Total" }
            decimalCell(4) { total }
        }
    }
}

fun RowsBuilderApi<InvoiceLineItem>.invoiceTermsAndInstructions(trailingRowIndex: Int = 0) {
    trailingRow(trailingRowIndex) {
        textCell(0) { "Thank You for your business!" }
    }
    trailingRow {
        textCell(0) { "Terms & Instructions" }
    }
}

fun Iterable<InvoiceLineItem>.printInvoice(
    fileName: String,
    issuer: CompanyAddress,
    client: CompanyAddress,
    invoiceNumber: String = "#00001",
    issueDate: LocalDate = LocalDate.now(),
    dueDate: LocalDate = LocalDate.now(),
) {
    val items = this
    tabulate(fileName) {
        columns {
            column(InvoiceLineItem::description)
            column(InvoiceLineItem::qty)
            column(InvoiceLineItem::unitPrice)
            column(InvoiceLineItem::vat)
            column(InvoiceLineItem::total)
        }
        rows {
            invoiceHeaderRow()
            companyHeaderRows {
                this.issuer = issuer
                imageUrl = "src/main/resources/logo.png"
            }
            separatorRow()
            invoiceShippingDetailsRow {
                this.invoiceNumber = invoiceNumber
                this.issuer = issuer
                this.client = client
                this.issueDate = issueDate
                this.dueDate = dueDate
            }
            separatorRow()
            invoiceItemsHeaderRow()
            // footer {  } // TODO : BUG - when I skip footer which is 0 index trailing row - rendering of remaining rows stops as there is a gap when next trailing row starts at 1.
            invoiceSummaryRow{
                subtotal = items.sumOf { it.unitPrice.multiply(it.qty.toBigDecimal()) }
                discounts = BigDecimal.ZERO
                taxes = items.sumOf { it.vat.multiply(it.unitPrice.multiply(it.qty.toBigDecimal())) }
                total = items.sumOf { it.total }
            }
            invoiceTermsAndInstructions()
        }
    }
}