package com.example.githubapp.retrofit

import com.example.githubapp.models.GitHubApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("{path}/{subPath1}/{subPath2}/{subPath3}")
    suspend fun getResults(
        @Path("path") path: String,
        @Path("subPath1") subPath1: String,
        @Path("subPath2") subPath2: String,
        @Path("subPath3") subPath3: String,
        @Query("state") query: String,
        @Header("Authorization") token: String
    ): Response<GitHubApiResponse>
}