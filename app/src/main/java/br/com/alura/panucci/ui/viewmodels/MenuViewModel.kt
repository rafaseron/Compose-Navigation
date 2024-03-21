package br.com.alura.panucci.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MenuUiState (val listMenu: List<Product> = emptyList())

class MenuViewModel(stateHolder: MenuUiState): ViewModel(){
    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()
}