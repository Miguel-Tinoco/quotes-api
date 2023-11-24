package com.challenge.quotes.models

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "quotes")
class Quote (
    val _id: String,
    val quoteText: String,
    val quoteAuthor: String,
    val quoteGenre: String,
    val __v: Int
)
