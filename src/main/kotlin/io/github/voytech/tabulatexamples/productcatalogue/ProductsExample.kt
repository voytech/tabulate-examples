package io.github.voytech.tabulatexamples.productcatalogue

import io.github.voytech.tabulate.api.builder.dsl.header
import io.github.voytech.tabulate.model.attributes.cell.Colors
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultBorderStyle
import io.github.voytech.tabulate.model.attributes.cell.enums.DefaultWeightStyle
import io.github.voytech.tabulate.model.attributes.cell.text
import io.github.voytech.tabulate.reactor.template.tabulate
import io.github.voytech.tabulate.template.TabulationFormat
import io.github.voytech.tabulate.template.context.AttributedCell
import io.github.voytech.tabulate.template.context.AttributedRow
import io.github.voytech.tabulate.template.context.RenderingContext
import io.github.voytech.tabulate.template.operations.ExportOperationsConfiguringFactory
import io.github.voytech.tabulate.template.operations.TableExportOperations
import io.github.voytech.tabulate.template.result.ResultProvider
import io.github.voytech.tabulatexamples.allBorders
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers.boundedElastic
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.lang.Thread.sleep
import java.math.BigDecimal

fun main(args: Array<String>) {
    Flux.fromIterable((1..5)
        .map { Product(id = it.toLong(), price = BigDecimal.ZERO, name = "Product $it") }
    ).publishOn(boundedElastic()).log().tabulate("products.xlsx") {
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
    }.subscribe {
        println(it)
    }
    sleep(50000)
}
