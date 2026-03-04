package com.example.pleasework.feature.spaceX.data.network

import com.example.pleasework.feature.spaceX.data.model.RocketLaunchDto
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ILaunchApi {
    suspend fun getAllLaunches(): List<RocketLaunchDto>
}

class LaunchApi(private val httpClient: HttpClient) : ILaunchApi {
    override suspend fun getAllLaunches(): List<RocketLaunchDto> {
        val url = "https://api.spacexdata.com/v5/launches"

        return httpClient
            .get(url) {
                parameter("limit", 10) // ? =
                headers {
                    append("Accept", "application/json")
                }
                contentType(ContentType.Application.Json)
            }.body()
    }
}