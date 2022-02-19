@file:Suppress("DuplicatedCode")

package io.github.voytech.tabulatexamples.contracts

import io.github.voytech.tabulate.api.builder.dsl.*
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.background
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultCellFill
import io.github.voytech.tabulate.model.attributes.cell.text
import io.github.voytech.tabulate.model.attributes.column.columnWidth
import io.github.voytech.tabulate.template.tabulate
import java.math.BigDecimal
import java.time.LocalDate

object TableDefinitions {

    val contractsTable = Table<Contract> {
        name = "Active contracts"
        attributes {
            columnWidth { auto = true }
        }
        columns {
            column(Contract::client)
            column(Contract::contractCode)
            column(Contract::contractLength)
            column(Contract::dateSigned)
            column(Contract::expirationDate)
            column(Contract::dateOfFirstPayment)
            column(Contract::lastPaymentDate)
            column(Contract::monthlyGrossValue)
        }
        rows {
            matching { header() }
            header {
                columnTitles("Client", "Code", "Contract Length", "Date Signed", "Expiration Date", "First Payment", "Last Payment","Monthly Gross Value")
                attributes {
                    text { fontColor = Colors.WHITE }
                    background {
                        color = Colors.BLACK
                    }
                }
            }
        }
    }
}

fun main() {
    val contracts = listOf(
        Contract(
            client = "Apollo",
            contractCode = "2011/12/AP",
            contractType = ContractType.FIXED_PRICE,
            contractLength = 12,
            dateSigned = LocalDate.parse("2011-12-23"),
            expirationDate = LocalDate.parse("2012-12-23"),
            dateOfFirstPayment = LocalDate.parse("2011-12-31"),
            lastPaymentDate = LocalDate.parse("2012-12-23"),
            monthlyGrossValue = BigDecimal.valueOf(200)
        ),
        Contract(
            client = "Columbia",
            contractCode = "2021/12/AP",
            contractType = ContractType.FIXED_PRICE,
            contractLength = 12,
            dateSigned = LocalDate.parse("2021-12-13"),
            expirationDate = LocalDate.parse("2022-12-13"),
            dateOfFirstPayment = LocalDate.parse("2021-12-31"),
            lastPaymentDate = LocalDate.parse("2022-12-13"),
            monthlyGrossValue = BigDecimal.valueOf(250)
        )
    )

    contracts.tabulate("contracts.xlsx") {
        name = "Active Contracts"
        columns {
            column(Contract::client)
            column(Contract::contractCode)
            column(Contract::contractLength)
            column(Contract::dateSigned)
            column(Contract::expirationDate)
            column(Contract::dateOfFirstPayment)
            column(Contract::lastPaymentDate)
            column(Contract::monthlyGrossValue)
        }
        attributes {
            columnWidth { auto = true }
        }
        rows {
            header {
                columnTitles("Client", "Code", "Contract Length", "Date Signed", "Expiration Date", "First Payment", "Last Payment","Monthly Gross Value")
                attributes {
                    text { fontColor = Colors.WHITE }
                    background {
                        color = Colors.BLACK
                    }
                }
            }
        }
    }


   contracts.tabulate("past_contracts.xlsx", TableDefinitions.contractsTable with Table { name = "Past contracts"})
}
