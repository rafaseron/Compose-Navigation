package br.com.alura.panucci.dao

import br.com.alura.panucci.model.Product
import br.com.alura.panucci.sampledata.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal

class ProductDao{

    private val _products = MutableStateFlow(sampleProducts)
    val products = _products.asStateFlow()

}