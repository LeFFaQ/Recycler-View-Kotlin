package com.lffq.recyclerviewtest.network

import com.google.gson.annotations.SerializedName

data class Latest(

	@field:SerializedName("news")
	val news: List<NewsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class NewsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("published")
	val published: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: List<String?>? = null,

	@field:SerializedName("url")
	val url: String? = null
)
