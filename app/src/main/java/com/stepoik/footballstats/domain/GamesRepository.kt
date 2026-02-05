package com.stepoik.footballstats.domain

import com.stepoik.footballstats.data.dto.GameDto
import com.stepoik.footballstats.data.dto.LeagueDto
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime

interface GamesRepository {
    suspend fun getGames(offset: Int): Result<List<GameDto>>

    suspend fun searchGame(leagueId: Long?, startDate: LocalDate?, endDate: LocalDate?, offset: Int): Result<List<GameDto>>

    suspend fun getGame(id: Long): Result<GameDto>

    suspend fun getLeagues(): Result<List<LeagueDto>>
}