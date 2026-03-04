package com.example.pleasework.feature.spaceX.domain.usecase

import com.example.pleasework.feature.spaceX.data.repository.ILaunchRepository
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch

interface IGetLaunchesUseCase{
    suspend operator fun invoke(forceReload: Boolean = false): List<RocketLaunch>
}
class GetLaunchesUseCase(
    private val launchRepository: ILaunchRepository
): IGetLaunchesUseCase {
    override suspend operator fun invoke(forceReload: Boolean): List<RocketLaunch> {
        return launchRepository.getLaunches(forceReload)
    }
}