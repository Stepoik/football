package com.stepoik.footballstats.ui.features.root

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.stepoik.footballstats.ui.features.details.GameDetails
import com.stepoik.footballstats.ui.features.main.MainScreen
import com.stepoik.footballstats.ui.features.search.SearchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Root(component: RootComponent) {
    Scaffold {
        Column {
            Children(
                component.stack, modifier = Modifier
                    .padding(it)
                    .weight(1f)
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.Account -> {

                    }

                    is RootComponent.Child.Search -> {
                        SearchScreen(instance.component)
                    }

                    is RootComponent.Child.Details -> {
                        GameDetails(instance.component)
                    }

                    is RootComponent.Child.Home -> {
                        MainScreen(instance.component)
                    }
                }
            }
            val active = component.stack.subscribeAsState().value.active.instance
            NavigationBar(Modifier.fillMaxWidth()) {
                NavigationBarItem(
                    selected = active is RootComponent.Child.Home,
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    onClick = { component.onHomeClicked() }
                )
                NavigationBarItem(
                    selected = active is RootComponent.Child.Search,
                    icon = { Icon(Icons.Default.Search, contentDescription = null) },
                    onClick = { component.onSearchClicked() }
                )
            }
        }
    }
}