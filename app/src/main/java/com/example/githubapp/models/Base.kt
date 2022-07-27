package com.example.githubapp.models

data class Base(
    val label: String,
    val ref: String,
    val repo: Repo,
    val sha: String,
    val user: User
)