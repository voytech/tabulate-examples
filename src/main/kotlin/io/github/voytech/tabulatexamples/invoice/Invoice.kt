package io.github.voytech.tabulatexamples.invoice

import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    listOf(
        InvoiceLineItem("Laptop: Acer", 1,BigDecimal.valueOf(2333.33),BigDecimal.valueOf(0.23)),
        InvoiceLineItem("Monitor: Lenovo", 1,BigDecimal.valueOf(1333.33),BigDecimal.valueOf(0.23)),
        InvoiceLineItem("Keyboard: Genesys 110", 1,BigDecimal.valueOf(233.99),BigDecimal.valueOf(0.23)),
        InvoiceLineItem("Headset: Syperlux HD330", 1,BigDecimal.valueOf(134.99),BigDecimal.valueOf(0.23)),
        InvoiceLineItem("Mouse: Logitech M185", 1,BigDecimal.valueOf(34.99),BigDecimal.valueOf(0.23)),
        InvoiceLineItem("IPhone 11", 1,BigDecimal.valueOf(3004.99),BigDecimal.valueOf(0.23)),
        InvoiceLineItem("DynaDesk", 1,BigDecimal.valueOf(1234.99),BigDecimal.valueOf(0.23)),
    ).printInvoice(
        fileName = "invoice.xlsx",
        invoiceNumber = "#00001",
        invoiceIssueDate = LocalDate.now(),
        invoiceDueDate = LocalDate.now(),
        issuerDetails =  CompanyAddress(
            contactName = "Brad Kovalsky",
            companyName = "Best Computers",
            address = "Macintosh Square St. 1/22",
            address2 = "brad@bestcomputers.com",
            phone = "988-324-342"
        ),
        clientDetails = CompanyAddress(
            contactName = "Jeremy Cooper",
            companyName = "JerCo.",
            address = "Genuine St. 22/202",
            address2 = "jerco@gmail.com",
            phone = "435-324-555"
        )
    )
}
