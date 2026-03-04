package com.example.pleasework.feature.spaceX.domain.repository

import com.example.pleasework.feature.spaceX.data.dao.ILaunchLocalDataSource
import com.example.pleasework.feature.spaceX.data.mapper.toDomain
import com.example.pleasework.feature.spaceX.data.model.ResultState
import com.example.pleasework.feature.spaceX.data.network.ILaunchApi
import com.example.pleasework.feature.spaceX.data.network.LaunchApi
import com.example.pleasework.feature.spaceX.data.repository.ILaunchRepository
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch

class LaunchRepository(
    private val api: ILaunchApi,
    private val localDataSource: ILaunchLocalDataSource
) : ILaunchRepository {
    override suspend fun getLaunches(
        forceReload: Boolean
    ): ResultState<List<RocketLaunch>> {
        return try {
            val cached = localDataSource.getAllLaunches()
            if (cached.isNotEmpty() && !forceReload) {
                ResultState.Success(cached)
            } else {
                val remoteLaunches = api.getAllLaunches().map { it.toDomain() }
                localDataSource.clearAndCreateLaunches(remoteLaunches)
                println("Api Result: $remoteLaunches")
                ResultState.Success(remoteLaunches)
            }
        } catch (e: Exception) {
            ResultState.Error(e.message ?: "Something went wrong")
        }
    }
}