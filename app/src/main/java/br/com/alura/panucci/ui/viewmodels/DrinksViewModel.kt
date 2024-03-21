package br.com.alura.panucci.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DrinksUiState (val listDrinks: List<Product> = emptyList())

class DrinksViewModel(stateHolder: DrinksUiState): ViewModel(){
    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    private val listDrinks = ProductDao().products

    init {
        _uiState.value = _uiState.value.copy(listDrinks = listDrinks.value)
    }

}