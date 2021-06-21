package io.github.voytech.tabulatexamples.productcatalogue

import java.math.BigDecimal
import java.util.*

data class Product(
    val id: Long,
    val code: String = UUID.randomUUID().toString().replace("-",""),
    val catalogueId: String = UUID.randomUUID().toString().replace("-",""),
    val name: String,
    val price: BigDecimal
)