import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MoldeDeUIState(val texto: String = "")

class MoldeDeViewModel(stateHolder: MoldeDeUIState = MoldeDeUIState()): ViewModel() {

    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    fun mudarTexto(newText: String){
        _uiState.value = _uiState.value.copy(texto = newText)
    }


}