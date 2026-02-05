package com.stepoik.footballstats.ui.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.stepoik.footballstats.ui.features.main.GameItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

const val POSTS_PAGINATION_THRESHOLD = 5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(component: SearchComponent) {
    val state = component.state.subscribeAsState().value
    var datePickerState by remember { mutableStateOf<DatePickerState?>(null) }
    Surface {
        Column {
            Row {
                Card(
                    modifier = Modifier.weight(1f),
                    onClick = { datePickerState = DatePickerState.START }) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(state.startDate.toString())
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    onClick = { datePickerState = DatePickerState.END }) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(state.endDate.toString())
                    }
                }
            }
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {}
            ) {
                Box(Modifier.fillMaxWidth()) {
                    Text(state.selectedLeagues?.name ?: "Выберите лигу")
                }
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { },
                    shape = RoundedCornerShape(bottomStart = 7.dp, bottomEnd = 7.dp)
                ) {
                    state.availableLeagues.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                expanded = false
                                component.onSelectLeague(it)
                            }
                        )
                    }
                }
            }
            val lazyState = rememberLazyListState()
            LaunchedEffect(Unit) {
                snapshotFlow { lazyState.firstVisibleItemIndex + lazyState.layoutInfo.visibleItemsInfo.size }.collect {
                    if (it > state.games.size - POSTS_PAGINATION_THRESHOLD) {
                        component.onLoadNext()
                    }
                }
            }
            LazyColumn {
                items(state.games, key = { it.id }) {
                    GameItem(it, onClick = { component.onGameSelected(it.id) })
                }
            }
        }
    }
    if (datePickerState != null) {
        val datePickerCompState = rememberDatePickerState()
        DatePickerDialog(onDismissRequest = { datePickerState = null }, confirmButton = {
            when (datePickerState) {
                DatePickerState.START -> datePickerCompState.selectedDateMillis?.let {
                    component.onSelectStartDate(it.toDate())
                }

                DatePickerState.END -> datePickerCompState.selectedDateMillis?.let {
                    component.onSelectEndDate(it.toDate())
                }

                else -> {}
            }
        }) {

            DatePicker(state = datePickerCompState)
        }
    }
}

fun Long.toDate(): LocalDate {
    return kotlinx.datetime.Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
}

enum class DatePickerState {
    START,
    END
}