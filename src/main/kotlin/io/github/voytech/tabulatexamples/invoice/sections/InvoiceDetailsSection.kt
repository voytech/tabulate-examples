package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder
import io.github.voytech.tabulatexamples.layoutsdsl.field
import io.github.voytech.tabulatexamples.layoutsdsl.heading
import java.time.LocalDate

class InvoiceDetailsBuilder {
    lateinit var number: String
    lateinit var dueDate: LocalDate
    lateinit var issueDate: LocalDate
}

fun SectionsBuilder<InvoiceLineItem>.invoiceDetailsSection(block: InvoiceDetailsBuilder.() -> Unit) {
    section {
        InvoiceDetailsBuilder().apply(block).let {
            heading("INVOICE DETAILS",3)
            field(column = 1, fieldName = "Invoice number:", fieldValue = it.number)
            field(column = 1, fieldName = "Issue date:", fieldValue = it.issueDate)
            field(column = 1, fieldName = "Payment due date:", fieldValue = it.issueDate)
        }
    }
}
