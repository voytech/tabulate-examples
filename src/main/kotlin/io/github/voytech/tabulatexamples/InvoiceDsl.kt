package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi
import io.github.voytech.tabulate.api.builder.dsl.cell
import io.github.voytech.tabulate.api.builder.dsl.footer
import io.github.voytech.tabulate.model.CellType
import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultCellFill
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultHorizontalAlignment
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.row.height
import io.github.voytech.tabulate.template.context.IndexLabel
import io.github.voytech.tabulate.template.tabulate
import java.time.LocalDate


fun <T> RowsBuilderApi<T>.invoiceHeaderRow() {
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

fun <T> RowsBuilderApi<T>.companyHeaderRows(block: CompanyHeaderRowBuilder.() -> Unit) {
    with(CompanyHeaderRowBuilder().apply(block)) {
        row {
            textCell(colSpan = 3) { issuer.companyName }
            cell {
                colSpan = 2
                rowSpan = 4
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
    }
}

fun <T> RowsBuilderApi<T>.invoiceItemsHeaderRow() {
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

class DetailsAndShippingBuilder {
    lateinit var invoiceNumber: String
    lateinit var issueDate: LocalDate
    lateinit var dueDate: LocalDate
    lateinit var issuer: CompanyAddress
    lateinit var client: CompanyAddress
}

fun <T> RowsBuilderApi<T>.invoiceShippingDetailsRow(block: DetailsAndShippingBuilder.() -> Unit) {
    with(DetailsAndShippingBuilder().apply(block)) {
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

fun Iterable<InvoiceLineItem>.printInvoice(
    fileName: String,
    issuer: CompanyAddress,
    client: CompanyAddress,
    invoiceNumber: String = "#00001",
    issueDate: LocalDate = LocalDate.now(),
    dueDate: LocalDate = LocalDate.now(),
) {
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
                imageUrl = "src/main/resources/image.png"
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
            footer {  }
            row(1, IndexLabel.DATASET_PROCESSED) {
                textCell { "Thank You for your business!" }
            }
            row(2, IndexLabel.DATASET_PROCESSED) {
                textCell { "Terms & Instructions" }
            }
        }
    }
}