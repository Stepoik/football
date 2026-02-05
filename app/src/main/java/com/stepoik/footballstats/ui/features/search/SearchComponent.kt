package com.stepoik.footballstats.ui.features.search

import com.arkivanov.decompose.ComponentContext
import com.stepoik.footballstats.core.Component
import com.stepoik.footballstats.data.dto.LeagueDto
import kotlinx.datetime.LocalDate

interface SearchComponent : Component<SearchState> {
    fun onSelectStartDate(date: LocalDate)
    fun onSelectEndDate(date: LocalDate)

    fun onSelectLeague(leagueDto: LeagueDto)

    fun onGameSelected(id: Long)

    fun onLoadNext()

    interface Factory {
        fun create(componentContext: ComponentContext, onGameSelected: (Long) -> Unit): SearchComponent
    }
}