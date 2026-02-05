package com.stepoik.footballstats.core

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class ErrorMessage(
    val text: String,
    val id: Int = Random.nextInt()
)
