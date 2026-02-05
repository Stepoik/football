package com.stepoik.footballstats.ui.features.details

import com.arkivanov.decompose.ComponentContext
import com.stepoik.footballstats.core.BaseComponent
import com.stepoik.footballstats.core.ErrorMessage
import com.stepoik.footballstats.domain.GamesRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class GameDetailsComponentImpl(
    componentContext: ComponentContext,
    private val gameId: Long,
    private val onBack: () -> Unit,
    private val gamesRepository: GamesRepository
) : GameDetailsComponent, BaseComponent<GameDetailsState>(componentContext, GameDetailsState.serializer()) {
    init {
        loadDetails()
    }

    override fun initialState() = GameDetailsState()

    private fun loadDetails() {
        componentScope.launch {
            updateState { it.copy(isLoading = true) }
            gamesRepository.getGame(gameId).onSuccess { game ->
                println(game)
                updateState { it.copy(game = game, isLoading = false) }
            }.onFailure {
                println(it)
                updateState { it.copy(isLoading = false, error = ErrorMessage("Ошибка при загрузке данных")) }
            }
        }
    }

    override fun onBack() {
        onBack.invoke()
    }

    class Factory : GameDetailsComponent.Factory, KoinComponent {
        override fun create(
            componentContext: ComponentContext,
            gameId: Long,
            onBack: () -> Unit
        ): GameDetailsComponent {
            return getKoin().get { parametersOf(componentContext, gameId, onBack) }
        }
    }
}