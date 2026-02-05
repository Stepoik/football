package com.stepoik.footballstats.di

import com.stepoik.footballstats.core.koin.componentFactory
import com.stepoik.footballstats.core.koin.componentOf
import com.stepoik.footballstats.core.ktor.defaultKtorClient
import com.stepoik.footballstats.data.GamesRepositoryImpl
import com.stepoik.footballstats.domain.GamesRepository
import com.stepoik.footballstats.ui.features.details.GameDetailsComponent
import com.stepoik.footballstats.ui.features.details.GameDetailsComponentImpl
import com.stepoik.footballstats.ui.features.main.MainComponent
import com.stepoik.footballstats.ui.features.main.MainComponentImpl
import com.stepoik.footballstats.ui.features.root.RootComponent
import com.stepoik.footballstats.ui.features.root.RootComponentImpl
import com.stepoik.footballstats.ui.features.search.SearchComponent
import com.stepoik.footballstats.ui.features.search.SearchComponentImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val rootModule = module {
    single { defaultKtorClient() }
    single<GamesRepository> { GamesRepositoryImpl(get()) }

    componentOf(::MainComponentImpl) bind MainComponent::class
    componentFactory<MainComponent.Factory> { MainComponentImpl.Factory() }

    componentOf(::RootComponentImpl) bind RootComponent::class
    componentFactory<RootComponent.Factory> { RootComponentImpl.Factory() }

    componentOf(::GameDetailsComponentImpl) bind GameDetailsComponent::class
    componentFactory<GameDetailsComponent.Factory> { GameDetailsComponentImpl.Factory() }

    componentOf(::SearchComponentImpl) bind SearchComponent::class
    componentFactory<SearchComponent.Factory> { SearchComponentImpl.Factory() }
}