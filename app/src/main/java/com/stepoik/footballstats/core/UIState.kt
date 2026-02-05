package com.stepoik.footballstats.core

import kotlinx.serialization.Serializable

interface UIState

@Serializable
object EmptyState : UIState