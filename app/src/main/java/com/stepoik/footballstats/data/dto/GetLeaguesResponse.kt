package com.stepoik.footballstats.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetLeaguesResponse(
    val data: List<LeagueDto>
)