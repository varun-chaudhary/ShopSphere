package com.example.shopsphere.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopsphere.R
import com.example.shopsphere.ui.theme.ShopSphereTheme

@Composable
fun CardPaymentItem(
    modifier: Modifier = Modifier,
    icon: Int,
    name: String,
    isChoose: Boolean,
    onChoose: () -> Unit
) {
    Card(
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onChoose() }
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = name,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(
                        width = 100.dp,
                        height = 60.dp
                    )
            )
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = isChoose,
                onClick = onChoose
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardPaymentItemPreview() {
    ShopSphereTheme(dynamicColor = false) {
        CardPaymentItem(
            icon = R.drawable.icon_master_card,
            name = "Cash On Delivery",
            isChoose = true,
            onChoose = {}
        )
    }
}