package br.com.alura.panucci.ui.screens

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.panucci.R
import br.com.alura.panucci.ui.theme.PanucciTheme
import br.com.alura.panucci.ui.viewmodels.ProductDetailUiState
import coil.compose.AsyncImage

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUiState = ProductDetailUiState(),
    onClick: () -> Unit = {},
    tryAgainClick:() -> Unit = {},
    voltarClick:() -> Unit = {}
) {
    when(uiState.screenState){
        "Loading" -> Box(modifier = Modifier.fillMaxSize(1f)){
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize(1f)
                    .align(Alignment.Center)) }

            "Error" -> Column(modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Falha ao buscar o produto")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { tryAgainClick() }) {
                    Text(text = "Tentar novamente")
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = { voltarClick() }) {
                    Text(text = "Voltar")
                }

            }
                "Sucess" -> Column(
                    modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        ) {
        uiState.product?.let {
            product ->

            product.image?.let {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(product.name, fontSize = 24.sp)
                Text(product.price.toPlainString(), fontSize = 18.sp)
                Text(product.description)
                Button(
                    onClick = { onClick() },
                    Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Pedir")
                }
            }
        } ?: Column(modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Oops! tivemos falhas ao tentar mostrar nosso produtos a você D:")
        }
    }
        else -> Column(modifier = Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Oops! tivemos falhas ao tentar mostrar nosso produtos a você D:")
        }
    }

}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    PanucciTheme {
        Surface {
            ProductDetailsScreen(
                //product = sampleProducts.random(),
            )
        }
    }
}