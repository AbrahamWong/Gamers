package com.minotawr.gamers.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("name_original")
	val nameOriginal: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("description_raw")
	val descriptionRaw: String? = null,

	@field:SerializedName("metacritic")
	val metacritic: Int? = null,

	@field:SerializedName("metacritic_url")
	val metacriticUrl: String? = null,

	@field:SerializedName("metacritic_platforms")
	val metacriticPlatforms: List<Any>? = null,

	@field:SerializedName("released")
	val released: String? = null,

	@field:SerializedName("tba")
	val tba: Boolean? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("background_image")
	val backgroundImage: String? = null,

	@field:SerializedName("background_image_additional")
	val backgroundImageAdditional: String? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("rating_top")
	val ratingTop: Int? = null,

	@field:SerializedName("ratings_count")
	val ratingsCount: Int? = null,

	@field:SerializedName("tags")
	val tags: List<TagsResponse>? = null,

	@field:SerializedName("genres")
	val genres: List<GenreResponse>? = null,

	@field:SerializedName("dominant_color")
	val dominantColor: String? = null,
)

