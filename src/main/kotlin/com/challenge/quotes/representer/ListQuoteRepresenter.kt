package com.challenge.quotes.representer

import com.challenge.quotes.models.Quote
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(LowerCamelCaseStrategy::class)
data class ListQuoteRepresenter(
    val statusCode: String,
    val message: String,
    val pagination: Pagination,
    val totalQuotes: Long,
    val data: List<QuoteRepresenter>
)

@JsonNaming(LowerCamelCaseStrategy::class)
data class Pagination(
    val currentPage: Int,
    val nextPage: Int?,
    val totalPages: Int
)

@JsonNaming(LowerCamelCaseStrategy::class)
data class QuoteRepresenter(
    val _id: String,
    val quoteText: String,
    val quoteAuthor: String,
    val quoteGenre: String,
    val __v: Long
) {
    constructor(quote: Quote) : this(
        _id = quote.id,
        quoteText = quote.quoteText,
        quoteAuthor = quote.quoteAuthor,
        quoteGenre = quote.quoteGenre,
        __v = quote.version
    )
}
