package com.minotawr.gamers.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    @field:SerializedName("seo_title")
    val seoTitle: String? = null,

    @field:SerializedName("seo_description")
    val seoDescription: String? = null,

    @field:SerializedName("seo_keywords")
    val seoKeywords: String? = null,

    @field:SerializedName("seo_h1")
    val seoH1: String? = null,

    @field:SerializedName("noindex")
    val noIndex: Boolean? = null,

    @field:SerializedName("nofollow")
    val noFollow: Boolean? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("nofollow_collections")
    val noFollowCollections: List<String>? = null,
) : BaseListResponse<GameResponse>()