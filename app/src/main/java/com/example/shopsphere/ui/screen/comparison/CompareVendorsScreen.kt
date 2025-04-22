package com.example.shopsphere.ui.screen.comparison


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

data class VendorOffer(
    val vendorName: String,
    val price: Double,
    val rating: Float
)

val dummyVendors = listOf("Amazon", "Flipkart", "Meesho", "Snapdeal", "Ajio")

fun generateVendorOffers(basePrice: Double): List<VendorOffer> {
    return dummyVendors.map { vendor ->
        val priceDeviation = basePrice * Random.nextDouble(-0.1, 0.1) // +/-10%
        val finalPrice = (basePrice + priceDeviation).coerceAtLeast(1.0)
        val rating = Random.nextDouble(3.0, 5.0).toFloat()
        VendorOffer(vendor, "%.2f".format(finalPrice).toDouble(), rating)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareVendorsScreen(productId: Int, navController: NavController) {
    val basePrice = 499.0 // Simulate base price from productId
    val vendorOffers = generateVendorOffers(basePrice)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compare Vendors") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Comparing offers for Product #$productId",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(vendorOffers) { offer ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(offer.vendorName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("₹${offer.price}", color = MaterialTheme.colorScheme.primary)
                            }
                            RatingBar(rating = offer.rating)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBar(rating: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating.toInt()) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = Color(0xFFFFD700) // Gold color
            )
        }
        Text(
            text = "(${String.format("%.1f", rating)})",
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
