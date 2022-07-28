package com.arasvitkus.mesbglister.model.entities

object RandomArmy {
}data class Quotes(
    val docs: List<Doc>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)

data class Doc(
    val _id: String,
    val character: String,
    val dialog: String,
    val id: String,
    val movie: String
)