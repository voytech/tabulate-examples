@file:Suppress("DuplicatedCode")

package io.github.voytech.tabulatexamples.contracts

import io.github.voytech.tabulate.api.builder.dsl.customTable
import io.github.voytech.tabulate.api.builder.dsl.header
import io.github.voytech.tabulate.api.builder.dsl.plus
import io.github.voytech.tabulate.api.builder.dsl.table
import io.github.voytech.tabulate.excel.model.attributes.printing
import io.github.voytech.tabulate.model.attributes.Colors
import io.github.voytech.tabulate.model.attributes.cell.background
import io.github.voytech.tabulate.model.attributes.cell.text
import io.github.voytech.tabulate.model.attributes.column.columnWidth
import io.github.voytech.tabulate.template.tabulate
import org.apache.poi.hssf.usermodel.HeaderFooter
import java.math.BigDecimal
import java.time.LocalDate


private val contractsTable = table<Contract> {
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
        header {
            columnTitles(
                "Client",
                "Code",
                "Contract Length",
                "Date Signed",
                "Expiration Date",
                "First Payment",
                "Last Payment",
                "Monthly Gross Value"
            )
            attributes {
                text { fontColor = Colors.WHITE }
                background {
                    color = Colors.BLACK
                }
            }
        }
    }
}

private val printingSetup = customTable {
    attributes {
        printing {
            firstPrintableColumn = 0
            lastPrintableColumn = 7
            firstPrintableRow = 0
            lastPrintableRow = 2
            blackAndWhite = true
            footerCenter = "Page ${HeaderFooter.page()} of  ${HeaderFooter.numPages()}"
        }
    }
}


fun main() {
    listOf(
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
    ).tabulate("contracts.xlsx", printingSetup + contractsTable + table { name = "Past contracts" })

}
