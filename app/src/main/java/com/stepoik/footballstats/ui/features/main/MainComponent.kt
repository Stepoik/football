package com.stepoik.footballstats.ui.features.main

import com.arkivanov.decompose.ComponentContext
import com.stepoik.footballstats.core.Component

interface MainComponent : Component<MainScreenState> {
    fun onLoadNext()

    fun onGameClicked(gameId: Long)

    interface Factory {
        fun create(componentContext: ComponentContext, onGame: (Long) -> Unit): MainComponent
    }
}