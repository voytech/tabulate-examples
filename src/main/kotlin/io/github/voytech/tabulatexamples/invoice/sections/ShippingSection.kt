package io.github.voytech.tabulatexamples.invoice.sections

import io.github.voytech.tabulatexamples.invoice.CompanyAddress
import io.github.voytech.tabulatexamples.invoice.InvoiceLineItem
import io.github.voytech.tabulatexamples.layoutsdsl.SectionsBuilder
import io.github.voytech.tabulatexamples.layoutsdsl.field
import io.github.voytech.tabulatexamples.layoutsdsl.heading

class AddressRowsBuilder {
    lateinit var addressTitle: String
    lateinit var address: CompanyAddress
}

fun SectionsBuilder<InvoiceLineItem>.addressDetailsSection(block: AddressRowsBuilder.() -> Unit) {
    section {
        AddressRowsBuilder().apply(block).let {
            heading(it.addressTitle)
            field(fieldValue = it.address.companyName)
            field(fieldValue = it.address.contactName)
            field(fieldValue = it.address.address)
            field(fieldValue = it.address.phone)
        }
    }
}
