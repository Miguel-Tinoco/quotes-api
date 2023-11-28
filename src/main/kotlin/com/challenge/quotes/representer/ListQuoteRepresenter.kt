package com.challenge.quotes.representer

import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCamelCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(LowerCamelCaseStrategy::class)
data class ListQuoteRepresenter(
    val statusCode: String,
    val message: String,
    val pagination: Pagination,
    val totalQuotes: Long,
    val data: List<ReadQuoteRepresenter>
)
