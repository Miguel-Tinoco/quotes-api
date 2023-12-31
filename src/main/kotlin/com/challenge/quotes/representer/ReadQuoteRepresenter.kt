package com.challenge.quotes.representer

import com.challenge.quotes.models.Quote
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(LowerCamelCaseStrategy::class)
data class ReadQuoteRepresenter(
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
