@file:Suppress("DuplicatedCode")

package io.github.voytech.tabulatexamples.guslist

import io.github.voytech.tabulate.api.builder.dsl.CustomTable
import io.github.voytech.tabulate.api.builder.dsl.header
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.background
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultCellFill
import io.github.voytech.tabulate.model.attributes.cell.text
import io.github.voytech.tabulate.model.attributes.column.columnWidth
import io.github.voytech.tabulate.template.tabulate
import io.github.voytech.tabulate.api.builder.dsl.Table
import io.github.voytech.tabulate.api.builder.dsl.plus

object TableDefinitions {

    val addressTable = Table<Address> {
        name = "Traitors address list"
        attributes {
            columnWidth { auto = true }
        }
        columns {
            column(Address::firstName)
            column(Address::lastName)
            column(Address::street)
            column(Address::postCode)
        }
        rows {
            header {
                columnTitles("First Name", "Last Name", "Street", "Post Code")
                attributes {
                    text { fontColor = Colors.WHITE }
                    background {
                        color = Colors.BLACK
                        fill = DefaultCellFill.SOLID
                    }
                }
            }
        }
    }
}

fun main() {
    val listForGus = listOf(
        Address("Jesse", "Pinkman","New Mexico", "9809 Margo Street","87104"),
        Address("Walter", "White","New Mexico", "308 Negra Arroyo Lane","87045"),
        Address("Saul", "Goodman","New Mexico", "9800 Montgomery Blvd NE","87111"),
        Address("Mike", "Ehrmantraut","New Mexico", "204 Edith Blvd. NE","87102"),
    )

    listForGus.tabulate("address_list.xlsx") {
        name = "Traitors address list"
        columns {
            column(Address::firstName)
            column(Address::lastName)
            column(Address::street)
            column(Address::postCode)
        }
        attributes {
            columnWidth { auto = true }
        }
    }

    listForGus.tabulate("address_list_header.xlsx") {
        name = "Traitors address list"
        attributes {
            columnWidth { auto = true }
        }
        columns {
            column(Address::firstName)
            column(Address::lastName)
            column(Address::street)
            column(Address::postCode)
        }
        rows {
            header("First Name", "Last Name", "Street", "Post Code")
        }
    }


    listForGus.tabulate("address_list_header_style.xlsx") {
        name = "Traitors address list"
        attributes {
            columnWidth { auto = true }
        }
        columns {
            column(Address::firstName)
            column(Address::lastName)
            column(Address::street)
            column(Address::postCode)
        }
        rows {
            header {
                columnTitles("First Name", "Last Name", "Street", "Post Code")
                attributes {
                    text { fontColor = Colors.WHITE }
                    background {
                        color = Colors.BLACK
                        fill = DefaultCellFill.SOLID
                    }
                }
            }
        }
    }

   listForGus.tabulate("address_list_static_definition.xlsx", CustomTable { name = "Dealers address list"} + TableDefinitions.addressTable)
}
