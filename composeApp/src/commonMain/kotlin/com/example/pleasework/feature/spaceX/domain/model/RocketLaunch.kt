package com.example.pleasework.feature.spaceX.domain.model

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

data class RocketLaunch(
    val flightNumber: Int,
    val missionName: String,
    val launchDateUTC: String,
    val details: String?,
    val launchSuccess: Boolean?,
    val links: Links
) {
    var launchYear = Instant.parse(launchDateUTC).toLocalDateTime(TimeZone.UTC).year
}

data class Links(
    val patch: Patch?,
    val article: String?
)

data class Patch(
    val small: String?,
    val large: String?
)