package io.github.voytech.tabulatexamples.layoutsdsl

import io.github.voytech.tabulate.api.builder.dsl.customTable
import io.github.voytech.tabulate.template.export

fun main(args: Array<String>) {
    customTable {
        columns {
            (0..10).forEach { column(it){} }
        }
        rows {
            layout {
                horizontal {
                    section {
                        newRow {
                            cell { value = "S1(1,1)" }
                            cell { value = "S1(1,2)" }
                        }
                        newRow {
                            cell { value = "S1(2,1)" }
                            cell { value = "S1(2,1)" }
                        }
                    }
                    section {
                        newRow {
                            cell { value = "S2(1,1)" }
                            cell { value = "S2(1,2)" }
                        }
                        newRow {
                            cell { value = "S2(2,1)" }
                            cell { value = "S2(2,2)" }
                        }
                    }
                }
                horizontal {
                    section {
                        newRow {
                            cell { value = "S3(1,1)" }
                            cell { value = "S3(1,2)" }
                            cell { value = "S3(1,3)" }
                        }
                        newRow {
                            cell { value = "S3(2,1)" }
                            cell { value = "S3(2,1)" }
                        }
                    }
                    section {
                        newRow {
                            cell { value = "S4(1,1)" }
                            cell { value = "S4(1,2)" }
                        }
                        newRow {
                            cell { value = "S4(2,1)" }
                            cell { value = "S4(2,2)" }
                        }
                    }
                }
            }
        }
    }.export("layouts.xlsx")
}
