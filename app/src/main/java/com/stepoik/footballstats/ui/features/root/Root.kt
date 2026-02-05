package com.stepoik.footballstats.ui.features.root

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.stepoik.footballstats.ui.features.details.GameDetails
import com.stepoik.footballstats.ui.features.main.MainScreen
import com.stepoik.footballstats.ui.features.search.SearchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Root(component: RootComponent) {
    Scaffold {
        Children(component.stack, modifier = Modifier.padding(it)) {
            when (val instance = it.instance) {
                is RootComponent.Child.Account -> {

                }
                is RootComponent.Child.Search -> {
                    SearchScreen(instance.)
                }
                is RootComponent.Child.Details -> {
                    GameDetails(instance.component)
                }
                is RootComponent.Child.Home -> {
                    MainScreen(instance.component)
                }
            }
        }
    }
}