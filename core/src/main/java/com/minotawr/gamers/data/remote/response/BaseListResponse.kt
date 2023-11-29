package com.minotawr.gamers.data.remote.response

import com.google.gson.annotations.SerializedName

abstract class BaseListResponse<T>(
    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("previous")
    val previous: String? = null,

    @field:SerializedName("results")
    val results: List<T>? = null
)