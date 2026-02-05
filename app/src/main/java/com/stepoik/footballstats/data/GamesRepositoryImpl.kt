package com.stepoik.footballstats.data

import com.stepoik.footballstats.data.dto.GameDto
import com.stepoik.footballstats.data.dto.GetGameResponse
import com.stepoik.footballstats.data.dto.GetLeaguesResponse
import com.stepoik.footballstats.data.dto.GetMatchesResponse
import com.stepoik.footballstats.data.dto.LeagueDto
import com.stepoik.footballstats.domain.GamesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime

private const val BASE_URL = "https://api.sstats.net/Games"

class GamesRepositoryImpl(
    private val httpClient: HttpClient
) : GamesRepository {
    override suspend fun getGames(offset: Int): Result<List<GameDto>> {
        return runCatching {
            httpClient.get("$BASE_URL/list") {
                parameter("Upcoming", true)
                parameter("Offset", offset)
                parameter("Limit", 20)
            }.body<GetMatchesResponse>().data
        }
    }

    override suspend fun searchGame(
        leagueId: Long?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        offset: Int
    ): Result<List<GameDto>> {
        return runCatching {
            val condition = buildString {
                leagueId?.let {
                    append("LeagueId = $it ")
                }
                startDate?.let {
                    if (leagueId != null) {
                        append("And ")
                    }
                    append("LeagueId = $it ")
                }
            }
            httpClient.post("$BASE_URL/query") {
                setBody(
                    mapOf(
                        "condition" to condition,
                        "Offset" to offset,
                        "Limit" to 20
                    )
                )
            }.body<GetMatchesResponse>().data
        }
    }

    override suspend fun getGame(id: Long): Result<GameDto> {
        return runCatching {
            httpClient.get("$BASE_URL/$id").body<GetGameResponse>().data.game
        }
    }

    override suspend fun getLeagues(): Result<List<LeagueDto>> {
        return runCatching {
            httpClient.get("https://api.sstats.net/Leagues").body<GetLeaguesResponse>().data
        }
    }

}