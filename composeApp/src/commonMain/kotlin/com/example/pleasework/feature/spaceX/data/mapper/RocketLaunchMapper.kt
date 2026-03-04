package com.example.pleasework.feature.spaceX.data.mapper

import com.example.pleasework.feature.spaceX.data.model.LinksDto
import com.example.pleasework.feature.spaceX.data.model.PatchDto
import com.example.pleasework.feature.spaceX.data.model.RocketLaunchDto
import com.example.pleasework.feature.spaceX.domain.model.Links
import com.example.pleasework.feature.spaceX.domain.model.Patch
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch

fun RocketLaunchDto.toDomain(): RocketLaunch {
    return RocketLaunch(
        flightNumber = flightNumber,
        missionName = missionName,
        launchDateUTC = launchDateUTC,
        details = details,
        launchSuccess = launchSuccess,
        links = links.toDomain()
    )
}

private fun LinksDto.toDomain(): Links {
    return Links(
        patch = patch?.toDomain(),
        article = article
    )
}

private fun PatchDto.toDomain(): Patch {
    return Patch(
        small = small,
        large = large
    )
}