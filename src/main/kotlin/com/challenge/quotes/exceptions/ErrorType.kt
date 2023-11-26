package com.challenge.quotes.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND

enum class ErrorType(
    val httpStatus: HttpStatus,
    val message: String
) {
    QUOTE_NOT_FOUND(NOT_FOUND, "Quote Not Found"),
    UNHANDLED_ERROR(INTERNAL_SERVER_ERROR, "Unexpected error")
}
