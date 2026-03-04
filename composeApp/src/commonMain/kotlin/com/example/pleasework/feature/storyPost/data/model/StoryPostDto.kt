package com.example.pleasework.feature.storyPost.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryPostDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
)
