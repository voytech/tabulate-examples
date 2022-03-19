package io.github.voytech.tabulatexamples.layoutsdsl

import io.github.voytech.tabulate.api.builder.dsl.*
import io.github.voytech.tabulate.model.attributes.cell.enums.contract.CellType
import io.github.voytech.tabulate.template.context.AdditionalSteps
import kotlin.properties.Delegates

class SectionCellBuilderApi<T>(
    protected val cursor: LayoutCursor,
    private val cellBuilderApi: CellBuilderApi<T>
) {
    @set:JvmSynthetic
    @get:JvmSynthetic
    var value: Any? by cellBuilderApi::value

    @set:JvmSynthetic
    @get:JvmSynthetic
    var colSpan: Int by Delegates.vetoable(1) { _, _, newValue ->
        cellBuilderApi.colSpan = newValue
        cursor.getAndIncColumn(newValue - 1)
        true
    }

    @set:JvmSynthetic
    @get:JvmSynthetic
    var rowSpan: Int by Delegates.vetoable(1) { _, _, newValue ->
        cellBuilderApi.rowSpan = newValue
        cursor.setMaxRow(newValue - 1)
        true
    }

    @JvmSynthetic
    fun typeHint(block: () -> CellType) { cellBuilderApi.typeHint(block) }

    @JvmSynthetic
    fun attributes(block: CellLevelAttributesBuilderApi<T>.() -> Unit) {
        cellBuilderApi.attributes(block)
    }

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

    @JvmSynthetic
    fun cell(index: Int,block: SectionCellBuilderApi<T>.() -> Unit) {
        rowBuilderApi.cell(cursor.currentColumnIndex+index) {
            SectionCellBuilderApi(cursor,this).apply(block)
        }
    }

    @JvmSynthetic
    fun attributes(block: RowLevelAttributesBuilderApi<T>.() -> Unit) {
        rowBuilderApi.attributes(block)
    }

}

class SectionBuilderApi<T>(
    private val cursor: LayoutCursor,
    private val rowsBuilderApi: RowsBuilderApi<T>
) {

    @JvmSynthetic
    fun newRow(block: SectionRowBuilderApi<T>.() -> Unit) {
        val blockCall: RowBuilderApi<T>.() -> Unit = {
            SectionRowBuilderApi(cursor,this).apply(block)
        }
        cursor.steps?.let {
            rowsBuilderApi.newRow(cursor.getAndIncRow(1),it, blockCall)
        } ?: rowsBuilderApi.newRow(cursor.getAndIncRow(1), blockCall)
        cursor.onNewRow()
    }

}

enum class LayoutType {
    HORIZONTAL,
    VERTICAL
}

class LayoutCursor(internal val steps: AdditionalSteps? = null) {
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

    fun setMaxRow(index: Int = 1) {
        if (index>maxRowIndex) maxRowIndex = index
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

data class LayoutCursors(
    private val cursors: List<LayoutCursor> = listOf(LayoutCursor(AdditionalSteps.TRAILING_ROWS)),
    private val default: LayoutCursor = LayoutCursor()
) {
    fun getByStep(step: AdditionalSteps?): LayoutCursor =
        step?.let { s -> cursors.find { s == it.steps } } ?: default
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
    private val cursors: LayoutCursors,
    private val rowsBuilderApi: RowsBuilderApi<T>
) {
    fun vertical(step: AdditionalSteps? = null,block: SectionsBuilder<T>.() -> Unit) {
        cursors.getByStep(step).let { cursor ->
            cursor.layoutType = LayoutType.VERTICAL
            SectionsBuilder(cursor, rowsBuilderApi).apply(block).also {
                cursor.closeLayout()
            }
        }

    }

    fun horizontal(step: AdditionalSteps? = null,block: SectionsBuilder<T>.() -> Unit) {
        cursors.getByStep(step).let { cursor ->
            cursor.layoutType = LayoutType.HORIZONTAL
            SectionsBuilder(cursor, rowsBuilderApi).apply(block).also {
                cursor.closeLayout()
            }
        }
    }
}

fun <T> RowsBuilderApi<T>.layout(block: LayoutBuilder<T>.() -> Unit) {
    LayoutBuilder(LayoutCursors(),this).apply(block)
}





