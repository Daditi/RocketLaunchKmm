package com.example.pleasework.feature.spaceX.data.repository

import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch

interface ILaunchRepository {
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch>
}