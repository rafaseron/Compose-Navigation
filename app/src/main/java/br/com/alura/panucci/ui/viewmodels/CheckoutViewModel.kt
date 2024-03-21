package br.com.alura.panucci.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CheckoutUiState(val listProduct: List<Product> = emptyList())

class CheckoutViewModel(stateHolder: CheckoutUiState = CheckoutUiState()): ViewModel(){
    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    private val listProduct = ProductDao().products

    //para atualizar os dados do ViewModel dentro do proprio ViewModel, precisamos de um Bloco de Inializacao
    init {
        _uiState.value = _uiState.value.copy(listProduct = listProduct.value)
    }
    //caso contrario, poderia ser chamado por fora, em uma funcao como esta:
    fun copyList(receivedList: List<Product>){
        _uiState.value = _uiState.value.copy(listProduct = receivedList)
    }

}