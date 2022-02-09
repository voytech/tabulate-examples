package io.github.voytech.tabulatexamples.productcatalogue

import io.github.voytech.tabulate.api.builder.dsl.header
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.cell.text

import io.github.voytech.tabulate.template.tabulate
import io.github.voytech.tabulatexamples.allBorders

import java.math.BigDecimal

fun main(args: Array<String>) {
    (1..500_000).map { Product(id = it.toLong(), price = BigDecimal.ZERO, name = "Product $it") }
                .tabulate("products.xlsx") {
        columns {
            column(Product::id)
            column(Product::code)
            column(Product::catalogueId)
            column(Product::name)
            column(Product::price)
        }
        rows {
            header {
                columnTitles("ID", "CODE", "CATALOGUE_ID", "NAME", "PRICE")
                attributes {
                    text {
                        weight = DefaultWeightStyle.BOLD
                    }
                    allBorders {
                        color = Colors.BLACK
                        style = DefaultBorderStyle.DOUBLE
                    }
                }
            }
        }
    }
}
