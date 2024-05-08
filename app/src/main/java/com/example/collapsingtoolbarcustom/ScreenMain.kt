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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleHeaderScreen() {
    val listState = rememberLazyListState() //inner Android function, remember scroll state
    val items = remember { List(100) { "Item ${it + 1}" } } // Список с примерными элементами

    // Header size logic
    val scrollOffset = remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
    val maxHeaderHeight = 150.dp
    val minHeaderHeight = 56.dp
    val headerHeight = (maxHeaderHeight - (scrollOffset.value / 5).dp).coerceAtLeast(minHeaderHeight)

    // Tracking max scroll position
    val maxScrollOffset = remember { mutableStateOf(0) }

    // Логика для счётчика скроллинга
    val scrolledItems = remember { derivedStateOf { listState.firstVisibleItemIndex } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Scrolled: ${scrolledItems.value}",
                        fontSize = headerHeight.value.sp/3,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                },
                modifier = Modifier
                    .height(headerHeight)
                    .background(MaterialTheme.colorScheme.primary),
            )
        }
    ) {//A list of our items, it can be very long
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