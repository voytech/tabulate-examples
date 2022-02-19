package io.github.voytech.tabulatexamples.layoutsdsl

import io.github.voytech.tabulate.api.builder.dsl.RowBuilderApi
import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi

class LayoutRowBuilderApi<T>(private val layoutBuilderApi: LayoutBuilderApi<T>) {

}

class LayoutBuilderApi<T>(private val rowsBuilderApi: RowsBuilderApi<T>) {
    var rowIndex: Int = 0
    var columnIndex: Int = 0
    var rowSpan: Int = 1
    var colSpan: Int = 1

    @JvmSynthetic
    fun row(block: LayoutRowBuilderApi<T>.() -> Unit) {

    }

}

fun <T> RowsBuilderApi<T>.layout(block: LayoutBuilderApi<T>.() -> Unit) {
    LayoutBuilderApi(this).apply(block).let {

    }
}

class HorizontalLayoutBuilderApi<T>(val layoutBuilderApi: LayoutBuilderApi<T>) {
    var currentRowOffset: Int = 0
    var currentColumnOffset: Int = 0
}

fun <T> RowsBuilderApi<T>.horizontal(block: SectionBuilder.() -> Unit) {
    SectionBuilder().apply(block).let {

    }
}

data class SectionBuilder(
    var rowIndex : Int = 0,
    var columnIndex: Int = 0,
    var currentColumnIndex: Int = 0
)

fun <T> RowsBuilderApi<T>.section(block: SectionBuilder.() -> Unit) {
    SectionBuilder().apply(block).let {

    }
}