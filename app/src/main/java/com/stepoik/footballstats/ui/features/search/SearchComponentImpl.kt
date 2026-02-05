package com.stepoik.footballstats.ui.features.search

import com.arkivanov.decompose.ComponentContext
import com.stepoik.footballstats.core.BaseComponent
import com.stepoik.footballstats.data.dto.LeagueDto
import com.stepoik.footballstats.domain.GamesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class SearchComponentImpl(
    componentContext: ComponentContext,
    private val onGameSelected: (Long) -> Unit,
    private val gamesRepository: GamesRepository
) : SearchComponent, BaseComponent<SearchState>(componentContext = componentContext, SearchState.serializer()) {
    private var gamesJob: Job? = null
    private var searchJob: Job? = null

    init {
        loadLeagues()
    }

    override fun initialState() = SearchState()

    override fun onSelectStartDate(date: LocalDate) {
        updateState { it.copy(startDate = date) }
        loadNew()
    }

    override fun onSelectEndDate(date: LocalDate) {
        updateState { it.copy(endDate = date) }
        loadNew()
    }

    override fun onSelectLeague(leagueDto: LeagueDto) {
        updateState { it.copy(selectedLeagues = leagueDto) }
        loadNew()
    }

    override fun onGameSelected(id: Long) {
        onGameSelected.invoke(id)
    }

    override fun onLoadNext() {
        val state = state.value
        if (state.gamesLoading) return

        updateState { it.copy(gamesLoading = true) }
        gamesJob = componentScope.launch {
            gamesRepository.searchGame(leagueId = state.selectedLeagues?.id, offset = state.games.size, startDate = state.startDate, endDate = state.endDate).onSuccess { newGames ->
                updateState { it.copy(games = it.games + newGames, gamesLoading = false) }
            }.onFailure {
                if (it !is CancellationException) {
                    updateState { it.copy(gamesLoading = false) }
                }
            }
        }
    }

    private fun loadNew() {
        gamesJob?.cancel()
        searchJob?.cancel()
        val state = state.value
        updateState { it.copy(gamesLoading = true) }
        searchJob = componentScope.launch {
            gamesRepository.searchGame(leagueId = state.selectedLeagues?.id, offset = state.games.size, startDate = state.startDate, endDate = state.endDate).onSuccess { games ->
                updateState { it.copy(games = games, gamesLoading = false) }
            }.onFailure {
                updateState { it.copy(gamesLoading = false) }
            }
        }
    }


    private fun loadLeagues() {
        componentScope.launch {
            gamesRepository.getLeagues().onSuccess { leagueDtos ->
                updateState { it.copy(availableLeagues = leagueDtos) }
            }
        }
    }

    class Factory : SearchComponent.Factory, KoinComponent {
        override fun create(onGameSelected: (Long) -> Unit): SearchComponent {
            return getKoin().get { parametersOf(onGameSelected) }
        }
    }
}