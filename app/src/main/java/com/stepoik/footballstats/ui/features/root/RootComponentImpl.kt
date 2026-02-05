package com.stepoik.footballstats.ui.features.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.stepoik.footballstats.core.BaseComponent
import com.stepoik.footballstats.core.EmptyState
import com.stepoik.footballstats.ui.features.details.GameDetailsComponent
import com.stepoik.footballstats.ui.features.main.MainComponent
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parameterSetOf

class RootComponentImpl(
    componentContext: ComponentContext,
    private val homeComponentFactory: MainComponent.Factory,
    private val detailFactory: GameDetailsComponent.Factory
) :
    BaseComponent<EmptyState>(componentContext, EmptyState.serializer()), RootComponent {
    override fun initialState() = EmptyState

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        serializer = Config.serializer(),
        source = navigation,
        handleBackButton = true,
        initialConfiguration = Config.Home,
        childFactory = ::childFactory
    )

    private fun childFactory(config: Config, context: ComponentContext): RootComponent.Child {
        return when (config) {
            is Config.Home -> {
                RootComponent.Child.Home(
                    homeComponentFactory.create(
                        context,
                        onGame = { navigation.pushNew(Config.Details(it)) }),
                )
            }

            is Config.Search -> {
                RootComponent.Child.Search()
            }

            is Config.Account -> {
                RootComponent.Child.Account()
            }

            is Config.Details -> {
                RootComponent.Child.Details(
                    detailFactory.create(
                        context,
                        gameId = config.id,
                        onBack = { navigation.pop() })
                )
            }
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        object Home : Config()

        @Serializable
        object Search : Config()

        @Serializable
        object Account : Config()

        @Serializable
        data class Details(val id: Long) : Config()
    }

    class Factory : RootComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext): RootComponent {
            return getKoin().get { parameterSetOf(componentContext) }
        }
    }
}