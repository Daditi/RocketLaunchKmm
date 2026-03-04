package com.example.pleasework.feature.storyPost.data.network

import com.example.pleasework.feature.storyPost.data.model.StoryPostDto
import io.ktor.client.HttpClient

interface IStoryPosts {
    suspend fun getPosts(): List<StoryPostDto>
}

class StoryPosts(httpClient: HttpClient): IStoryPosts {
    override suspend fun getPosts(): List<StoryPostDto> {
        TODO("Not yet implemented")
    }

    companion object{
        const val URL= "https://dummyjson.com/posts?limit=10"
    }
}