package io.github.voytech.tabulatexamples.invoice

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