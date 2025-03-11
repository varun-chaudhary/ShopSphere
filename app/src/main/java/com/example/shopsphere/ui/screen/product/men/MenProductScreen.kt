package com.example.shopsphere.ui.screen.product.men

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopsphere.helper.ViewModelFactory
import com.example.shopsphere.ui.common.UiState
import com.example.shopsphere.ui.component.AnimatedShimmerProduct
import com.example.shopsphere.ui.component.ProductCard2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MenProductScreen(
    gridHeight: Dp = Dp.Unspecified,
    limit: Int,
    count: Int = 4,
    height: Dp,
    navigateToDetail: (Int) -> Unit,
    viewModel: MenProductViewModel = viewModel(
        factory = ViewModelFactory.getInstance(
            context = LocalContext.current,
            limit = limit
        )
    ),
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    viewModel.uiState.observeAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getProductByCategory("men's clothing", limit)
                Box(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(height)
                ){
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(196.dp),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .heightIn(min = gridHeight, max = gridHeight)
                    ) {
                        items(count){
                            AnimatedShimmerProduct()
                        }
                    }
                }
            }
            is UiState.Success -> {
                val product = uiState.data
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(196.dp),
                    modifier = Modifier.heightIn(min = gridHeight, max = gridHeight),
                ) {
                    items(product){
                        ProductCard2(
                            image = it.image,
                            title = it.title,
                            modifier = Modifier.clickable {
                                navigateToDetail(it.id)
                            },
                            rating = it.rating.rate.toString(),
                            price = it.price.toString(),
                            category = it.category,
                            addToCart = {
                                viewModel.addToCart(it)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Product added to cart"
                                    )
                                }
                            }
                        )
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(text = uiState.errorMessage)
                }
            }
        }
    }
}

