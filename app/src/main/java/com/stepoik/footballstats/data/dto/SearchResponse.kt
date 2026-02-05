package com.stepoik.footballstats.data.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant


@Serializable
data class SearchResponse(
    val data: List<Data>
) {
    @Serializable
    data class Data(
        val Id: Long,
        val HomeTeamName: String,
        val AwayTeamName: String,
        val Date: Instant,
        val LeagueName: String,
        val CountryName: String,
        val Status: Int
    ) {
        fun toGame(): GameDto {
            return GameDto(
                id = Id,
                date = Date,
                homeTeam = TeamDto(id = 0, name = HomeTeamName),
                awayTeam = TeamDto(id = 0, name = AwayTeamName),
                season = SeasonDto(
                    uid = "",
                    year = 2020,
                    league = LeagueDto(
                        id = 0,
                        name = LeagueName,
                        country = CountryDto(code = "", name = CountryName)
                    )
                ),
                statusName = Status.toString()
            )
        }
    }
}
