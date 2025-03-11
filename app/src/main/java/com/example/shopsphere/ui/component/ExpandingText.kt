package com.example.shopsphere.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.shopsphere.ui.theme.ShopSphereTheme
import com.example.shopsphere.ui.theme.poppinsFontFamily

private const val MINIMIZED_MAX_LINES = 3

@Composable
fun ExpandingText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(text) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                finalText = "$text Show less"
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val showMoreString = "...Show More"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == ',' }

                finalText = "$adjustedText$showMoreString"

                isClickable = true
            }
        }
    }

    val annotatedString = buildAnnotatedString {
        val showMoreIndex = finalText.indexOf("...Show More")
        val showLessIndex = finalText.indexOf("Show less")

        if (showMoreIndex != -1) {
            val endIndex = showMoreIndex + "...Show More".length
            append(finalText.substring(0, showMoreIndex))
            withStyle(style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold)
            ) {
                append(finalText.substring(showMoreIndex, endIndex))
            }
            append(finalText.substring(endIndex))
        } else if (showLessIndex != -1) {
            val endIndex = showLessIndex + "Show less".length
            append(finalText.substring(0, showLessIndex))
            withStyle(style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold)
            ) {
                append(finalText.substring(showLessIndex, endIndex))
            }
            append(finalText.substring(endIndex))
        } else {
            append(finalText)
        }
    }

    Text(
        fontSize = fontSize,
        fontFamily = poppinsFontFamily,
        color = MaterialTheme.colorScheme.outline,
        text = annotatedString,
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isClickable) { isExpanded = !isExpanded }
            .animateContentSize(),
    )
}

@Preview(showBackground = true)
@Composable
private fun ExpandingTextPreview() {
    ShopSphereTheme {
        ExpandingText(
            text = """
                Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.
            """.trimIndent(),
            fontSize = 14.sp
        )
    }
}