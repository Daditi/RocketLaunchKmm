package com.example.pleasework.data.di

import com.example.pleasework.presentation.spaceX.viewmodel.RocketLaunchViewModel
import com.example.pleasework.data.cache.AndroidDatabaseDriverFactory
import com.example.pleasework.cache.AppDatabase
import com.example.pleasework.core.cache.DatabaseDriverFactory
import com.example.pleasework.core.network.HttpClientFactory
import com.example.pleasework.feature.spaceX.data.dao.ILaunchLocalDataSource
import com.example.pleasework.feature.spaceX.data.dao.LaunchLocalDataSource
import com.example.pleasework.feature.spaceX.data.network.ILaunchApi
import com.example.pleasework.feature.spaceX.data.network.LaunchApi
import com.example.pleasework.feature.spaceX.data.repository.ILaunchRepository
import com.example.pleasework.feature.spaceX.domain.repository.LaunchRepository
import com.example.pleasework.feature.spaceX.domain.usecase.GetLaunchesUseCase
import com.example.pleasework.feature.spaceX.domain.usecase.IGetLaunchesUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(get()) }

    single { AppDatabase(get<DatabaseDriverFactory>().createDriver()) }

    single<ILaunchLocalDataSource> { LaunchLocalDataSource(get()) }

    single { HttpClientFactory.create() }

    single<ILaunchApi> { LaunchApi(get()) }

    single<ILaunchRepository> {
        LaunchRepository(get(), get())
    }

    factory<IGetLaunchesUseCase> {
        GetLaunchesUseCase(get())
    }

    viewModel { RocketLaunchViewModel(getLaunchesUseCase = get()) }
}