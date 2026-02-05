package com.stepoik.footballstats.core

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.value.Value

@Stable
interface Component<S : UIState> {
    val state: Value<S>
}