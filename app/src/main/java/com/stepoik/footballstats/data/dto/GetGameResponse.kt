package com.stepoik.footballstats.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetGameResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val game: GameDto
    )
}
