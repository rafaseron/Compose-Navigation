package br.com.alura.panucci.model

import java.math.BigDecimal
import java.util.UUID

class Product(
    val iD: String = UUID.randomUUID().toString(),
    val name: String,
    val price: BigDecimal,
    val description: String,
    val image: String? = null
)