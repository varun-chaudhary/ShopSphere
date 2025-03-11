package com.example.shopsphere.ui.screen.ourproduct

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.shopsphere.helper.ViewModelFactory
import com.example.shopsphere.model.ProductResponseItem
import com.example.shopsphere.ui.common.UiState
import com.example.shopsphere.ui.component.AnimatedShimmerProduct
import com.example.shopsphere.ui.component.ProductCard2
import com.example.shopsphere.ui.component.ProductNotFoundAnimation
import com.example.shopsphere.ui.component.SearchBar
import com.example.shopsphere.ui.navigation.model.Screen
import com.example.shopsphere.ui.theme.ShopSphereTheme
import com.example.shopsphere.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch


@Composable
fun OurProductScreen(
    navcontroller: NavHostController,
    viewModel: OurProductViewModel = viewModel(
        factory = ViewModelFactory
            .getInstance(LocalContext.current))
) {
    val sortValue by remember { mutableStateOf("asc") }
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)

    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = it.visuals.message,
                        fontFamily = poppinsFontFamily
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            SearchBar(
                onSearch = { query ->
                    if (query.isEmpty()) {
                        viewModel.sortProduct(sortValue)
                    } else {
                        viewModel.searchProduct(query)
                    }
                },
//                onSortChange = { sort ->
//                    sortValue = if (sort == "asc") "asc" else "desc"
//                    viewModel.sortProduct(sort)
//                }
            )
        }
    ) { innerPadding ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getAllProduct(20)
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ){
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(196.dp),
                        modifier = Modifier.heightIn(min = 1000.dp, max = 1000.dp)
                    ) {
                        items(10){
                            AnimatedShimmerProduct()
                        }
                    }
                }
            }
            is UiState.Success -> {
                val products = (uiState as UiState.Success<List<ProductResponseItem>>).data
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(196.dp),
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(items = products) { item ->
                        ProductCard2(
                            image = item.image,
                            title = item.title,
                            modifier = Modifier
                                .clickable {
                                    navcontroller.navigate(
                                        Screen
                                            .DetailProduct
                                            .createRoute(item.id)
                                    )
                                },
                            rating = item.rating.rate.toString(),
                            price = item.price.toString(),
                            category = item.category,
                            addToCart = {
                                viewModel.addToCart(item)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Product added to cart")
                                }
                            }
                        )
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .align(Alignment.Center)
                    ) {
                        ProductNotFoundAnimation(
                            modifier = Modifier
                        )
                        Text(
                            modifier = Modifier.offset(y = (-70).dp),
                            text = "Product Not Found",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyProductPreview() {
    ShopSphereTheme {
        OurProductScreen(rememberNavController())
    }
}