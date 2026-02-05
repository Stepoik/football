package com.stepoik.footballstats.ui.features.details

import com.stepoik.footballstats.core.ErrorMessage
import com.stepoik.footballstats.core.UIState
import com.stepoik.footballstats.data.dto.GameDto
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailsState(
    val game: GameDto? = null,
    val isLoading: Boolean = false,
    val error: ErrorMessage? = null
): UIState
