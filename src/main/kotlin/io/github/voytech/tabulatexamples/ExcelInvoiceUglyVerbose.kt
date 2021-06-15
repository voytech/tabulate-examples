package io.github.voytech.tabulatexamples

import io.github.voytech.tabulate.api.builder.dsl.cell
import io.github.voytech.tabulate.api.builder.dsl.footer
import io.github.voytech.tabulate.model.attributes.cell.*
import io.github.voytech.tabulate.model.attributes.cell.enums.*
import io.github.voytech.tabulate.model.attributes.row.height
import io.github.voytech.tabulate.template.tabulate
import java.math.BigDecimal
import java.time.LocalDate

data class InvoiceLineItem(
    val description: String,
    val qty: Int,
    val unitPrice: BigDecimal,
    val vat: BigDecimal,
    val total: BigDecimal = unitPrice.multiply(vat)
)

data class CompanyAddress(
    val contactName: String,
    val companyName: String,
    val address: String,
    val address2: String = "",
    val address3: String = "",
    val phone: String = ""
)

data class InvoiceDetails(
    val invoiceNumber: String,
    val issueDate: LocalDate,
    val dueDate: LocalDate,
    val issuerCompany: CompanyAddress,
    val clientCompany: CompanyAddress,
    val items: List<InvoiceLineItem>
)

fun main(args: Array<String>) {
    Inv.printInvoice(
        fileName = "invoice.xlsx",
        i = InvoiceDetails(
            invoiceNumber = "#00001",
            issueDate = LocalDate.now(),
            dueDate = LocalDate.now(),
            issuerCompany = CompanyAddress(
                contactName = "Joseph Lunar",
                companyName = "Always Good Computers",
                address = "Albatross St. 1/22",
                address2 = "joslun@gmail.com",
                phone = "988-324-342"
            ),
            clientCompany = CompanyAddress(
                contactName = "Jeremy Corpse",
                companyName = "JerCo.",
                address = "Genuine St. 22/22",
                address2 = "jerco@gmail.com",
                phone = "435-324-555"
            ),
            items = listOf(
                InvoiceLineItem("Laptop Acer", 1,BigDecimal.valueOf(2333.33),BigDecimal.valueOf(0.23)),
                InvoiceLineItem("Monitor Lenovo", 1,BigDecimal.valueOf(1333.33),BigDecimal.valueOf(0.23)),
                InvoiceLineItem("Mechanical Keyboard Genesys", 1,BigDecimal.valueOf(233.99),BigDecimal.valueOf(0.23)),
                InvoiceLineItem("Mouse - Logitech M185", 1,BigDecimal.valueOf(34.99),BigDecimal.valueOf(0.23)),
            )
        )
    )
}

object Inv{
    fun printInvoice(fileName: String, i: InvoiceDetails) {
        i.items.tabulate(fileName) {
            firstRow = 1
            firstColumn = 1
            columns {
                column(InvoiceLineItem::description)
                column(InvoiceLineItem::qty)
                column(InvoiceLineItem::unitPrice)
                column(InvoiceLineItem::vat)
                column(InvoiceLineItem::total)
            }
            rows {
                row {
                    attributes { height { px = 200 } }
                    cell {
                        value = i.issuerCompany.companyName
                        attributes {
                            text {
                                weight = DefaultWeightStyle.BOLD
                                fontSize = 16
                            }
                            alignment {
                                vertical = DefaultVerticalAlignment.MIDDLE
                                horizontal = DefaultHorizontalAlignment.LEFT
                            }
                        }
                    }
                    cell(InvoiceLineItem::total) {
                        value = "INVOICE"
                        attributes {
                            text {
                                weight = DefaultWeightStyle.BOLD
                                fontSize = 20
                            }
                            alignment {
                                vertical = DefaultVerticalAlignment.MIDDLE
                                horizontal = DefaultHorizontalAlignment.RIGHT
                            }
                        }
                    }
                }
                row { cell { value =  i.issuerCompany.companyName } }
                row { cell { value = i.issuerCompany.address } }
                row { cell { value = i.issuerCompany.phone } }
                row { }
                row {
                    cells {
                        cell {
                            value = "BILL TO"
                            attributes {
                                text { weight = DefaultWeightStyle.BOLD }
                                borders {
                                    bottomBorderColor = Colors.BLACK
                                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                                }
                            }
                        }
                        cell {
                            value = "SHIP TO"
                            attributes {
                                text { weight = DefaultWeightStyle.BOLD }
                                borders {
                                    bottomBorderColor = Colors.BLACK
                                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                                }
                            }
                        }
                        cell { }
                        cell {
                            value = "Invoice No:"
                            attributes {
                                text { weight = DefaultWeightStyle.BOLD }
                                borders {
                                    bottomBorderColor = Colors.BLACK
                                    bottomBorderStyle = DefaultBorderStyle.DOUBLE
                                }
                            }
                        }
                    }
                    cell {
                        value = i.invoiceNumber
                        attributes {
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
                    cell { value = i.issuerCompany.companyName }
                    cell { value = i.clientCompany.companyName }
                    cell { }
                    cell { value = "Invoice Date:"}
                    cell { value = i.issueDate}
                }
                row {
                    cell { value = i.issuerCompany.contactName }
                    cell { value = i.clientCompany.contactName }
                    cell { }
                    cell { value = "Due Date:"}
                    cell { value = i.dueDate}
                }
                row {
                    cell { value = i.issuerCompany.address }
                    cell { value = i.clientCompany.address }
                }
                row {
                    cell { value = i.issuerCompany.phone }
                    cell { value = i.clientCompany.phone }
                }
                row { }
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
                footer {

                }
            }
        }
    }
}
