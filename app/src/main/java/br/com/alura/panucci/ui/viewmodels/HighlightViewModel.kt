package br.com.alura.panucci.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HighlightUiState (val listDestaques: List<Product> = emptyList())

class HighlightViewModel(stateHolder: HighlightUiState): ViewModel(){
    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    private val listDestaques = ProductDao().products

    init {
        _uiState.value = _uiState.value.copy(listDestaques = listDestaques.value)
    }
}