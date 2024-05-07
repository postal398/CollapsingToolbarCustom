package com.example.collapsingtoolbarcustom

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


    @Composable
    fun Screen() {
        val scrollState = rememberScrollState()
        val maxScrollValue = scrollState.maxValue

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                ShrinkingHeader(
                    scrollState = scrollState,
                    maxScrollValue = maxScrollValue.toFloat()
                )

                Items(count = 100)
            }
        }
    }

    @Composable
    fun ShrinkingHeader(scrollState: ScrollState, maxScrollValue: Float) {
        val scrollOffset = (scrollState.value / maxScrollValue).coerceIn(0f, 1f)
        val shrinkFactor = 1 - scrollOffset

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = shrinkFactor
                    scaleY = shrinkFactor
                }
        ) {
            Text(
                text = "Header",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "Scroll offset: ${scrollState.value.toInt()}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    @Composable
    fun Items(count: Int) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(count) {
                ItemRow(item = "Item $it")
            }
        }
    }

    @Composable
    fun ItemRow(item: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item)
            Icon(
                painter = painterResource(id = androidx.core.R.drawable.ic_call_answer_video),
                contentDescription = null
            )
        }
    }

