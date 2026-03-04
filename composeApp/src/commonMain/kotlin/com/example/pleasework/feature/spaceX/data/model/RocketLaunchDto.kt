package com.example.pleasework.feature.spaceX.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunchDto(
@SerialName("flight_number")
val flightNumber: Int,
@SerialName("name")
val missionName: String,
@SerialName("date_utc")
val launchDateUTC: String,
@SerialName("details")
val details: String?,
@SerialName("success")
val launchSuccess: Boolean?,
@SerialName("links")
val links: LinksDto
)

@Serializable
data class LinksDto(
    @SerialName("patch")
    val patch: PatchDto?,
    @SerialName("article")
    val article: String?
)

@Serializable
data class PatchDto(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)