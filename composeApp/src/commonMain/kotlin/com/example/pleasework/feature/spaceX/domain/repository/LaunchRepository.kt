package com.example.pleasework.feature.spaceX.domain.repository

import com.example.pleasework.feature.spaceX.data.dao.ILaunchLocalDataSource
import com.example.pleasework.feature.spaceX.data.network.LaunchApi
import com.example.pleasework.feature.spaceX.data.repository.ILaunchRepository
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch

class LaunchRepository(
    private val api: LaunchApi,
    private val localDataSource: ILaunchLocalDataSource
) : ILaunchRepository {
    override suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = localDataSource.getAllLaunches()

        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            val remoteLaunches = api.getAllLaunches()
            localDataSource.clearAndCreateLaunches(remoteLaunches)
            remoteLaunches
        }
    }
}