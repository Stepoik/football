package com.stepoik.footballstats.ui.features.main

import com.arkivanov.decompose.ComponentContext
import com.stepoik.footballstats.core.BaseComponent
import com.stepoik.footballstats.domain.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class MainComponentImpl(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
    private val onGame: (Long) -> Unit,
) : BaseComponent<MainScreenState>(
    componentContext = componentContext,
    serializer = MainScreenState.serializer()
), MainComponent {
    init {
        onLoadNext()
    }

    override fun initialState() = MainScreenState()
    override fun onLoadNext() {
        val state = state.value
        if (state.isLoading) return

        updateState { it.copy(isLoading = true) }
        componentScope.launch(Dispatchers.Default) {
            gamesRepository.getGames(state.games.size).onSuccess { newGames ->
                println(newGames)
                updateState { it.copy(games = LinkedHashSet(it.games + newGames).toList(), isLoading = false) }
            }.onFailure {
                println(it)
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    override fun onGameClicked(gameId: Long) {
        onGame(gameId)
    }

    class Factory : MainComponent.Factory, KoinComponent {
        override fun create(componentContext: ComponentContext, onGame: (Long) -> Unit): MainComponent {
            return getKoin().get { parametersOf(componentContext, onGame) }
        }
    }
}