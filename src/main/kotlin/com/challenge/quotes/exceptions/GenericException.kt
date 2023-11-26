package com.challenge.quotes.exceptions

abstract class GenericException : Throwable {
    val errorType: ErrorType

    constructor(errorType: ErrorType) : super(errorType.message) {
        this.errorType = errorType
    }
}
