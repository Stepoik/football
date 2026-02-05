package com.stepoik.footballstats.ui.features.search

import com.stepoik.footballstats.core.UIState
import com.stepoik.footballstats.data.dto.GameDto
import com.stepoik.footballstats.data.dto.LeagueDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SearchState(
    val availableLeagues: List<LeagueDto> = listOf(),
    val selectedLeagues: LeagueDto? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val games: List<GameDto> = listOf(),
    val gamesLoading: Boolean = false,
): UIState
