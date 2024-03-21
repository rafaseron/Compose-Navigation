package br.com.alura.panucci.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProductDetailUiState (val product: Product? = null)

class ProductDetailViewModel(stateHolder: ProductDetailUiState): ViewModel(){
    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    private val dao = ProductDao()
    private val product = dao.products

    init {
        for (p in product.value){
            val produtoPesquisado = dao.findByIdWithResponse(p.iD)
            _uiState.value = _uiState.value.copy(product = produtoPesquisado)
        }
    }
}