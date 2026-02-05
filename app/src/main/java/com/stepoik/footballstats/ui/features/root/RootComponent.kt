package com.stepoik.footballstats.ui.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.stepoik.footballstats.core.Component
import com.stepoik.footballstats.core.EmptyState
import com.stepoik.footballstats.ui.features.details.GameDetailsComponent
import com.stepoik.footballstats.ui.features.main.MainComponent
import com.stepoik.footballstats.ui.features.search.SearchComponent

interface RootComponent : Component<EmptyState> {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Home(val component: MainComponent) : Child()
        data class Search(val component: SearchComponent) : Child()
        class Account() : Child()
        data class Details(val component: GameDetailsComponent) : Child()
    }

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}