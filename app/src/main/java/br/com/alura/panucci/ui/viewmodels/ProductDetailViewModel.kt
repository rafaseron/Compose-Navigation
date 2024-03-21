package br.com.alura.panucci.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProductDetailUiState (val product: Product? = null, val screenState: String = "Loading")

class ProductDetailViewModel(stateHolder: ProductDetailUiState = ProductDetailUiState()): ViewModel(){
    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    private val dao = ProductDao()
    private val product = dao.products

    fun findByIdWithResponse(id: String){
        val listProducts = product.value
        val searchProduct = listProducts.find {
                p ->
                p.iD == id
        }

        searchProduct?.let {
            _uiState.value = _uiState.value.copy(product = searchProduct)
            _uiState.value = _uiState.value.copy(screenState = "Sucess")
        } ?: onFailure()
    }

    fun onFailure(){
        _uiState.value = _uiState.value.copy(screenState = "Error")
    }
}