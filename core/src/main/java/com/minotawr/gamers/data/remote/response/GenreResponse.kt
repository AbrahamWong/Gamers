package com.minotawr.gamers.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,
)