import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp

import kotlin.math.max

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleHeaderScreen() {
    val listState = rememberLazyListState()
    val items = remember { List(100) { "Item ${it + 1}" } }

    val maxHeaderHeight = 150.dp
    val minHeaderHeight = 66.dp

    // Отслеживаем максимальную позицию скролла
    val maxScrollOffset = remember { mutableStateOf(0) }

    // Вычисляем высоту заголовка на основе максимальной позиции скролла
    val headerHeight by derivedStateOf {
        if (listState.firstVisibleItemIndex == 0) {
            maxScrollOffset.value = max(maxScrollOffset.value, listState.firstVisibleItemScrollOffset)
            max(maxHeaderHeight - (maxScrollOffset.value / 5).dp, minHeaderHeight)
        } else {
            minHeaderHeight
        }
    }

    // Сбрасываем maxScrollOffset если вернулись к первому элементу
    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (listState.firstVisibleItemIndex == 0) {
            maxScrollOffset.value = 0
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = "Scrolled: ${listState.firstVisibleItemIndex}",
                        fontSize = headerHeight.value.sp/4
                    )
                },
                modifier = Modifier.height(headerHeight).background(Color.Cyan),
//                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        LazyColumn(state = listState) {
            items(items) { item ->
                ListItem(item)
            }
        }
    }
}

@Composable
fun ListItem(item: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(modifier = Modifier.padding(24.dp)) {
            Text(text = item)
        }
    }
}