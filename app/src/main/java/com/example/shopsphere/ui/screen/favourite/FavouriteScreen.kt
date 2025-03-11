package com.example.shopsphere.ui.screen.favourite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shopsphere.R
import com.example.shopsphere.data.local.entity.Favourite
import com.example.shopsphere.helper.ViewModelFactory
import com.example.shopsphere.ui.component.BoxEmptyAnimation
import com.example.shopsphere.ui.component.FavouriteItem
import com.example.shopsphere.ui.navigation.model.Screen
import com.example.shopsphere.ui.theme.ShopSphereTheme
import com.example.shopsphere.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navController: NavController,
    viewModel: FavouriteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(
            context = LocalContext.current,
        )
    )
) {
    val favouriteItems by viewModel.allFavorites.observeAsState(emptyList())

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                title = {
                    Text(
                        text = stringResource(R.string.favourite),
                        fontFamily = poppinsFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            if (favouriteItems.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .align(Alignment.Center)
                ) {
                    BoxEmptyAnimation(
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = "You don't have any favourite items",
                        fontFamily = poppinsFontFamily,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                FavouriteContent(
                    state = favouriteItems,
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteContent(
    modifier: Modifier = Modifier,
    state: List<Favourite>,
    navController: NavController
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(items = state){ product ->
            AnimatedVisibility(
                modifier = Modifier.animateItemPlacement(tween(100)),
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            ) {
                FavouriteItem(
                    imageId = product.productImage,
                    productName = product.productName,
                    category = product.productCategory,
                    price = product.productPrice,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.DetailProduct.createRoute(product.productId))
                        }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
private fun FavouriteScreenPreview() {
    ShopSphereTheme {
        FavouriteContent(
            state = listOf(
                Favourite(
                    productId = 1,
                    productName = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                    productPrice = "78",
                    productImage = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                    productCategory = "men's clothing",
                    productQuantity = 1
                )
            ),
            navController = rememberNavController()
        )
    }
}