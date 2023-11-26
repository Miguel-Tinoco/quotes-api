package com.challenge.quotes.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "Quotes")
class Quote(
    @Id
    val id: String,

    val quoteText: String,

    val quoteAuthor: String,

    val quoteGenre: String,

    @Field("__v")
    val version: Long
)
