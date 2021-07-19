package com.example.newsfeed.data.models

import java.io.Serializable

data class Source(
    val id: Any?,
    val name: String,
    val url: String
) : Serializable
