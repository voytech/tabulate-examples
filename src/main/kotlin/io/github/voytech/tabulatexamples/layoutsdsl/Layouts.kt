package io.github.voytech.tabulatexamples.layoutsdsl

import io.github.voytech.tabulate.api.builder.dsl.CellBuilderApi
import io.github.voytech.tabulate.api.builder.dsl.RowBuilderApi
import io.github.voytech.tabulate.api.builder.dsl.RowsBuilderApi

class SectionCellBuilderApi<T>(
    private val cursor: LayoutCursor,
    private val cellBuilderApi: CellBuilderApi<T>
) {
    @set:JvmSynthetic
    @get:JvmSynthetic
    var value: Any? by cellBuilderApi::value
}

class SectionRowBuilderApi<T>(
    private val cursor: LayoutCursor,
    private val rowBuilderApi: RowBuilderApi<T>
) {

    @JvmSynthetic
    fun cell(block: SectionCellBuilderApi<T>.() -> Unit) {
        rowBuilderApi.cell(cursor.getAndIncColumn(1)) {
           SectionCellBuilderApi(cursor,this).apply(block)
        }
    }

}

class SectionBuilderApi<T>(
    private val cursor: LayoutCursor,
    private val rowsBuilderApi: RowsBuilderApi<T>
) {

    @JvmSynthetic
    fun newRow(block: SectionRowBuilderApi<T>.() -> Unit) {
        rowsBuilderApi.newRow(cursor.getAndIncRow(1)) {
            SectionRowBuilderApi(cursor,this).apply(block)
        }.also {
            cursor.onNewRow()
        }
    }

}

enum class LayoutType {
    HORIZONTAL,
    VERTICAL
}

class LayoutCursor {
    var currentRowIndex: Int = 0
    var sectionRowIndex: Int = 0
    var maxRowIndex: Int = 0
    var currentColumnIndex: Int = 0
    var sectionColumnIndex: Int = 0
    var maxColumnIndex: Int = 0
    var layoutType: LayoutType = LayoutType.HORIZONTAL

    fun getAndIncRow(index: Int = 1): Int {
        return currentRowIndex.also {
            currentRowIndex+=index
            if (currentRowIndex>maxRowIndex) maxRowIndex = currentRowIndex
        }
    }

    fun getAndIncColumn(index: Int = 1): Int {
        return currentColumnIndex.also {
            currentColumnIndex+=index
            if (currentColumnIndex>maxColumnIndex) maxColumnIndex = currentColumnIndex
        }
    }

    fun onNewRow() {
        currentColumnIndex = sectionColumnIndex
    }

    fun closeLayout() {
        sectionRowIndex = maxRowIndex
        currentRowIndex = maxRowIndex
        currentColumnIndex = 0
        sectionColumnIndex = 0
        maxColumnIndex = 0
    }

    fun closeSection() {
        if (layoutType == LayoutType.HORIZONTAL) {
            sectionColumnIndex = maxColumnIndex
        } else {
            sectionRowIndex = maxRowIndex
        }
        currentRowIndex = sectionRowIndex
        currentColumnIndex = sectionColumnIndex
    }
}

data class SectionsBuilder<T>(
    private val cursor: LayoutCursor,
    private val rowsBuilderApi: RowsBuilderApi<T>
) {
    fun section(block: SectionBuilderApi<T>.() -> Unit) {
        SectionBuilderApi(cursor, rowsBuilderApi).apply(block).also {
            cursor.closeSection()
        }
    }
}

data class LayoutBuilder<T>(
    private val cursor: LayoutCursor,
    private val rowsBuilderApi: RowsBuilderApi<T>
) {
    fun vertical(block: SectionsBuilder<T>.() -> Unit) {
        cursor.layoutType = LayoutType.VERTICAL
        SectionsBuilder(cursor, rowsBuilderApi).apply(block).also {
            cursor.closeLayout()
        }
    }

    fun horizontal(block: SectionsBuilder<T>.() -> Unit) {
        cursor.layoutType = LayoutType.HORIZONTAL
        SectionsBuilder(cursor, rowsBuilderApi).apply(block).also {
            cursor.closeLayout()
        }
    }
}

fun <T> RowsBuilderApi<T>.layout(block: LayoutBuilder<T>.() -> Unit) {
    LayoutBuilder(LayoutCursor(),this).apply(block)
}





