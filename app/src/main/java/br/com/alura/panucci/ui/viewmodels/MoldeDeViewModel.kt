import androidx.lifecycle.ViewModel
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MoldeDeUIState(val texto: String = "", val listProducts: List<Product> = emptyList())

class MoldeDeViewModel(stateHolder: MoldeDeUIState = MoldeDeUIState()): ViewModel() {

    private val _uiState = MutableStateFlow(stateHolder)
    val uiState = _uiState.asStateFlow()

    private val dao = ProductDao()
    val listProduct = dao.products.value
    //pegando uma lista de uma fonte externa para mudar o valor da List do UiState

    //init para que o proprio ViewModel consiga mudar o valor dele mesmo sozinho
    init {
        _uiState.value = _uiState.value.copy(listProducts = listProduct)
    }

    fun mudarTexto(newText: String){
        _uiState.value = _uiState.value.copy(texto = newText)
    }


}