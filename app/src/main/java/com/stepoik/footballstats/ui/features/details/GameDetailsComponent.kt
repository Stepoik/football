package com.stepoik.footballstats.ui.features.details

import com.arkivanov.decompose.ComponentContext
import com.stepoik.footballstats.core.Component

interface GameDetailsComponent : Component<GameDetailsState> {
    fun onBack()

    interface Factory {
        fun create(componentContext: ComponentContext, gameId: Long, onBack: () -> Unit): GameDetailsComponent
    }
}