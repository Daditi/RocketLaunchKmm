package com.example.pleasework

import com.example.pleasework.cache.AndroidDatabaseDriverFactory
import com.example.pleasework.network.SpaceXApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val appModule = module {
    single<SpaceXApi> { SpaceXApi() }
    single<SpaceXSDK> {
        SpaceXSDK(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ), api = get()
        )
    }

    viewModel { RocketLaunchViewModel(sdk = get()) }
}