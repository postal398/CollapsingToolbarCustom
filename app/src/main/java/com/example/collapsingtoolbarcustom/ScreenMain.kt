import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
    // Тут содержится инфа об лэйзи листе - скроллится ли он, можно ли ещё вверх-вниз, первый видимый элемент
    val listState = rememberLazyListState()

    val items = remember { List(100) { "Item ${it + 1}" } }

    val maxHeaderHeight = 160.dp
    val minHeaderHeight = 90.dp

    //БАЗА!
    // listState.firstVisibleItemScrollOffset - скролл внутри отдельно взятого айтема
    //listState.firstVisibleItemIndex - первый видимый элемент внутри списка
    //listState.layoutInfo.viewportSize.height - высота видимой области (viewport) списка в пикселях, на пикселе 3120



    // Отслеживаем максимальную позицию скролла
    val maxScrollOffset = remember { mutableStateOf(0) }

    //Отслеживаем предыдущую позицию скроллла
    var previousScrollOffset = remember { 0 }



    var volumeOfOffset = remember { true }
    val volumeOfOffsetTAG = "currentScrollOffset"
    Log.d(volumeOfOffsetTAG, "Вниз или вверх: ${volumeOfOffset}")


    // Отслеживание изменений состояния прокрутки путём
    //Умножения первого видимого элемента списка на  высоту видимой области и прибавки скролла одного итема
    LaunchedEffect(listState.firstVisibleItemScrollOffset, listState.firstVisibleItemIndex) {
        val currentScrollOffset = listState.firstVisibleItemIndex * listState.layoutInfo.viewportSize.height + listState.firstVisibleItemScrollOffset

        volumeOfOffset = if (currentScrollOffset > previousScrollOffset) {
            volumeOfOffset
        } else if (currentScrollOffset < previousScrollOffset) {
            !volumeOfOffset
        } else {
            volumeOfOffset
        }



//         Обновление предыдущего смещения скролла
        previousScrollOffset = currentScrollOffset

        val myOffsetTAG = "currentScrollOffset"
        Log.d(myOffsetTAG, "Общий счётчик скролла: ${currentScrollOffset}")

        val previosOffsetTag = "previosOffsetTag"
        Log.d(previosOffsetTag, "Предыдущий оффсет: ${previousScrollOffset}")
    }



    // Управление высотой заголовка
    val targetHeight  by derivedStateOf {

        val scrollUp = listState.firstVisibleItemScrollOffset < previousScrollOffset
        previousScrollOffset = listState.firstVisibleItemScrollOffset //запоминаем первый видимый элемент

        if (scrollUp || listState.firstVisibleItemIndex == 0) {
            //обновляется максимальное смещение скролла
            maxScrollOffset.value = max(maxScrollOffset.value, listState.firstVisibleItemScrollOffset)
            //Чем больше пользователь скроллит вниз, тем больше maxScrollOffset.value и тем меньше высота заголовка.
            // Однако высота заголовка не может быть меньше minHeaderHeight.
            max(maxHeaderHeight - (maxScrollOffset.value / 5).dp, minHeaderHeight)
        } else {
            minHeaderHeight
        }
    }



//Просто графон
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(top = 35.dp, bottom = 0.dp),
                        text = "Scrolled: ${listState.firstVisibleItemIndex} и пред. ${"тест"}",
                        fontSize = targetHeight.value.sp / 4
                    )
                },
                modifier = Modifier
                    .height(targetHeight)
                    .background(Color.Cyan),
            )
        }
    ) {
        LazyColumn(state = listState, contentPadding = PaddingValues(top = maxHeaderHeight, bottom = 50.dp)) {
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