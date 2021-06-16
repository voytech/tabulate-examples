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


fun <T> RowsBuilderApi<T>.invoiceHeaderSection(companyName: String) {
    val invoiceLabel = "INVOICE"
    row {
        attributes { height { px = 160 } }
        cell {
            value = companyName
            boldText()
            horizontallyAligned()
        }
        separatorCell(span = 2)
        cell {
            colSpan = 2
            value = invoiceLabel
            boldText(16)
            horizontallyAligned(align = DefaultHorizontalAlignment.RIGHT)
        }
    }
}

fun <T> RowsBuilderApi<T>.companyLogoSection(imageUrl: String) {
    row {
        separatorCell(3)
        cell {
            colSpan = 2
            rowSpan = 4
            value = imageUrl
            type = CellType.IMAGE_URL
            attributes {
                borders {
                    bottomBorderColor = Colors.BLACK
                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                    leftBorderColor = Colors.BLACK
                    leftBorderStyle = DefaultBorderStyle.DOUBLE
                    topBorderColor = Colors.BLACK
                    topBorderStyle = DefaultBorderStyle.DOUBLE
                    rightBorderColor = Colors.BLACK
                    rightBorderStyle = DefaultBorderStyle.DOUBLE
                }
            }
        }
    }
}

fun <T> RowsBuilderApi<T>.invoiceItemsHeader() {
    row {
        cell { value = "DESCRIPTION" }
        cell { value = "QTY" }
        cell { value = "UNIT PRICE" }
        cell { value = "VAT" }
        cell { value = "TOTAL" }
        attributes {
            borders {
                leftBorderColor = Colors.BLACK
                leftBorderStyle = DefaultBorderStyle.SOLID
                rightBorderColor = Colors.BLACK
                rightBorderStyle = DefaultBorderStyle.SOLID
                topBorderColor = Colors.BLACK
                topBorderStyle = DefaultBorderStyle.SOLID
                bottomBorderColor = Colors.BLACK
                bottomBorderStyle = DefaultBorderStyle.SOLID
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
            invoiceHeaderSection(issuer.companyName)
            companyLogoSection(imageUrl = "src/main/resources/image.png")
            row {
                cell {
                    value = issuer.address
                    colSpan = 3
                }
            }
            row {
                cell {
                    value = issuer.phone
                    colSpan = 3
                }
            }
            separatorRow()
            row {
                cells {
                    cell {
                        value = "BILL TO"
                        doubleUnderlineBoldStyle()
                    }
                    cell {
                        value = "SHIP TO"
                        doubleUnderlineBoldStyle()
                    }
                    separatorCell()
                    cell {
                        value = "Invoice No:"
                        doubleUnderlineBoldStyle()
                    }
                }
                cell {
                    value = invoiceNumber
                    attributes {
                        alignment { horizontal = DefaultHorizontalAlignment.CENTER }
                        borders {
                            bottomBorderColor = Colors.BLACK
                            bottomBorderStyle = DefaultBorderStyle.DOUBLE
                        }
                    }
                }
            }
            row {
                matching { it.hasRecord() }
                cell { attributes { text { weight = DefaultWeightStyle.BOLD }} }
            }
            row {
                cell { value = issuer.companyName }
                cell { value = client.companyName }
                separatorCell()
                cell { value = "Invoice Date:"}
                dateCell(issueDate)
            }
            row {
                cell { value = issuer.contactName }
                cell { value = client.contactName }
                separatorCell()
                cell { value = "Due Date:"}
                dateCell(dueDate)
            }
            row {
                cell { value = issuer.address }
                cell { value = client.address }
            }
            row {
                cell { value = issuer.phone }
                cell { value = client.phone }
            }
            separatorRow()
            invoiceItemsHeader()
            footer { }
            row(1, IndexLabel.DATASET_PROCESSED) {
                cell {
                    colSpan = 2
                    value = "Thank You for your business!"
                }
            }
            row(2, IndexLabel.DATASET_PROCESSED) {
                cell {
                    colSpan = 2
                    value = "Terms & Instructions"
                }
            }
        }
    }
}