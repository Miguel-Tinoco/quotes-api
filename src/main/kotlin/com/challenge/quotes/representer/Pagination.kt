package com.challenge.quotes.representer

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy::class)
data class Pagination(
    val currentPage: Int,
    val nextPage: Int?,
    val totalPages: Int
)
