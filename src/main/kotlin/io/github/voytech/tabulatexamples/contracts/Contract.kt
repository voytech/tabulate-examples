package io.github.voytech.tabulatexamples.contracts

import java.math.BigDecimal
import java.time.LocalDate

data class Contract(
    val client: String,
    val contractCode: String,
    val contractType: ContractType,
    val contractLength: Int,
    val dateSigned: LocalDate,
    val expirationDate: LocalDate,
    val dateOfFirstPayment: LocalDate,
    val lastPaymentDate: LocalDate,
    val monthlyGrossValue: BigDecimal

)

enum class ContractType {
    FIXED_PRICE,
    TIME_AND_MATERIALS
}