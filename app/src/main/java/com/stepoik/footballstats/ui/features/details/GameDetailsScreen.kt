package com.stepoik.footballstats.ui.features.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.stepoik.footballstats.data.dto.GameDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetails(component: GameDetailsComponent) {
    val state = component.state.subscribeAsState().value
    if (state.isLoading) {

    } else if (state.game != null) {
        GameDetails(state.game, component)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameDetails(state: GameDto, component: GameDetailsComponent) {
    Surface {
        Column(Modifier) {
            TopAppBar(title = { Text("Match Details") }, navigationIcon = {
                IconButton(onClick = component::onBack) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                }
            })
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.season.league.name)
                Text("${state.homeTeam.name} vs ${state.awayTeam.name}")
                Text("${state.date}")
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Country")
                    Text(state.season.league.country.name)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Status")
                    Text(state.statusName ?: "")
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Match ID")
                    Text(state.id.toString())
                }
            }
        }
    }
}