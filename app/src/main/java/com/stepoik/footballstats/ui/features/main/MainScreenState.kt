package com.stepoik.footballstats.ui.features.main

import com.stepoik.footballstats.core.UIState
import com.stepoik.footballstats.data.dto.GameDto
import kotlinx.serialization.Serializable

@Serializable
data class MainScreenState(
    val games: List<GameDto> = listOf(),
    val isLoading: Boolean = false
): UIState
