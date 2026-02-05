package com.stepoik.footballstats.data.dto

import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class GetMatchesResponse(
    val data: List<GameDto>
)

@Serializable
data class GameDto @OptIn(ExperimentalTime::class) constructor(
    val id: Long,
    val date: Instant?,
    val homeTeam: TeamDto,
    val awayTeam: TeamDto,
    val season: SeasonDto,
    val statusName: String?
)

@Serializable
data class TeamDto(
    val id: Long,
    val name: String
)

@Serializable
data class SeasonDto(
    val uid: String,
    val year: Int,
    val league: LeagueDto
)

@Serializable
data class LeagueDto(
    val id: Long,
    val name: String,
    val country: CountryDto
)

@Serializable
data class CountryDto(
    val code: String,
    val name: String
)
