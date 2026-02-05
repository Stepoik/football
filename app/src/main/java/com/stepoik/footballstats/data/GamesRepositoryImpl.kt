package com.stepoik.footballstats.data

import com.stepoik.footballstats.data.dto.GameDto
import com.stepoik.footballstats.data.dto.GetGameResponse
import com.stepoik.footballstats.data.dto.GetLeaguesResponse
import com.stepoik.footballstats.data.dto.GetMatchesResponse
import com.stepoik.footballstats.data.dto.LeagueDto
import com.stepoik.footballstats.data.dto.SearchResponse
import com.stepoik.footballstats.domain.GamesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import kotlin.String
import kotlin.time.Instant

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
            }
            httpClient.post("$BASE_URL/query") {
                setBody(
                    SearchRequest(
                        condition, offset, limit = 20, fields = listOf(
                            "Id",
                            "HomeTeamName",
                            "AwayTeamName",
                            "Date",
                            "LeagueName",
                            "CountryName",
                            "Status"
                        )
                    )
                )
            }.body<SearchResponse>().data.map { it.toGame() }
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

@Serializable
data class SearchRequest(
    val condition: String,
    val offset: Int,
    val limit: Int,
    val fields: List<String>
)