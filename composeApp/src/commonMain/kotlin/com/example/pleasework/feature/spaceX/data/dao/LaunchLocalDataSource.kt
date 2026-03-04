package com.example.pleasework.feature.spaceX.data.dao

import com.example.pleasework.cache.AppDatabase
import com.example.pleasework.feature.spaceX.domain.model.Links
import com.example.pleasework.feature.spaceX.domain.model.Patch
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch

interface ILaunchLocalDataSource{
    fun getAllLaunches(): List<RocketLaunch>
    fun clearAndCreateLaunches(launches: List<RocketLaunch>)
}

class LaunchLocalDataSource(private val database: AppDatabase):ILaunchLocalDataSource {
    private val dbQuery = database.appDatabaseQueries

    override fun getAllLaunches(): List<RocketLaunch> {
        return dbQuery.selectAllLaunchesInfo(::mapLaunchSelecting)
            .executeAsList()
    }

    override fun clearAndCreateLaunches(launches: List<RocketLaunch>) {
        dbQuery.transaction {
            dbQuery.removeAllLaunches()
            launches.forEach { launch ->
                dbQuery.insertLaunch(
                    flightNumber = launch.flightNumber.toLong(),
                    missionName = launch.missionName,
                    details = launch.details,
                    launchSuccess = launch.launchSuccess ?: false,
                    launchDateUTC = launch.launchDateUTC,
                    patchUrlSmall = launch.links.patch?.small,
                    patchUrlLarge = launch.links.patch?.large,
                    articleUrl = launch.links.article
                )
            }
        }
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        patchUrlSmall: String?,
        patchUrlLarge: String?,
        articleUrl: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            details = details,
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess,
            links = Links(
                patch = Patch(
                    small = patchUrlSmall,
                    large = patchUrlLarge
                ),
                article = articleUrl
            )
        )
    }
}