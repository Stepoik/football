package com.stepoik.footballstats.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.stepoik.footballstats.data.dto.GameDto

private const val POSTS_PAGINATION_THRESHOLD = 5

@Composable
fun MainScreen(component: MainComponent) {
    val state = component.state.subscribeAsState().value
    val lazyState = rememberLazyListState()
    LaunchedEffect(Unit) {
        snapshotFlow { lazyState.firstVisibleItemIndex + lazyState.layoutInfo.visibleItemsInfo.size }.collect {
            if (it > state.games.size - POSTS_PAGINATION_THRESHOLD) {
                component.onLoadNext()
            }
        }
    }
    Column {
        Box(
            Modifier.background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Football Stats",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        LazyColumn {
            item {
                Text("Popular Upcoming Matches", style = MaterialTheme.typography.titleLarge)
            }
            items(state.games, key = {
                it.id
            }) {
                GameItem(it, onClick = { component.onGameClicked(it.id) })
            }
        }
    }
}

@Composable
fun GameItem(game: GameDto, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(game.season.league.country.name)
            Text(game.date.toString())
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text(game.awayTeam.name)
            Column {
                Text(game.date.toString())
            }
            Text(game.homeTeam.name)
        }
        HorizontalDivider(Modifier.fillMaxWidth())
        Text(game.season.league.name)
    }
}