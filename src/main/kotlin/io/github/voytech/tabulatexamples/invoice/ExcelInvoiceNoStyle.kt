@file:Suppress("DuplicatedCode")

package io.github.voytech.tabulatexamples.invoice

import io.github.voytech.tabulate.api.builder.dsl.cell
import io.github.voytech.tabulate.api.builder.dsl.footer
import io.github.voytech.tabulate.api.builder.dsl.trailingRow
import io.github.voytech.tabulate.excel.model.attributes.dataFormat
import io.github.voytech.tabulate.model.attributes.row.height
import io.github.voytech.tabulate.template.tabulate
import java.math.BigDecimal
import java.time.LocalDate

fun main(args: Array<String>) {
    InvoiceWithoutStyle.printInvoice(
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

object InvoiceWithoutStyle {
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
                    attributes { height { px = 160 } }
                    cell { value = i.issuerCompany.companyName }
                    cell { colSpan = 2; value = "" }
                    cell { colSpan = 2; value = "INVOICE" }
                }
                row { cell { value =  i.issuerCompany.companyName } }
                row { cell { value = i.issuerCompany.address } }
                row { cell { value = i.issuerCompany.phone } }
                row { }
                row {
                    cells {
                        cell { value = "BILL TO" }
                        cell { value = "SHIP TO" }
                        cell { }
                        cell { value = "Invoice No:" }
                    }
                    cell { value = i.invoiceNumber }
                }
                row {
                    cell { value = i.issuerCompany.companyName }
                    cell { value = i.clientCompany.companyName }
                    cell { }
                    cell { value = "Invoice Date:"}
                    cell { value = i.issueDate; attributes { dataFormat { value = "yyyy-mm-dd" } } }
                }
                row {
                    cell { value = i.issuerCompany.contactName }
                    cell { value = i.clientCompany.contactName }
                    cell { }
                    cell { value = "Due Date:"}
                    cell { value = i.dueDate; attributes { dataFormat { value = "yyyy-mm-dd" } } }
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
                }
                footer { }
                trailingRow(1) {
                    cell { value = "Thank You for your business!" }
                }
                trailingRow(2) {
                    cell { value = "Terms & Instructions" }
                }
            }
        }
    }
}
