package com.challenge.quotes.handlers

import com.challenge.quotes.exceptions.ResourceNotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalExceptionHandler : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        return when (ex) {
            is ResourceNotFoundException -> {
                exchange.response.statusCode = ex.errorType.httpStatus
                exchange.response.headers.contentType = MediaType.APPLICATION_JSON
                val errorMessage = mapOf("message" to ex.message)
                val responseBody = exchange.response.bufferFactory().wrap(ObjectMapper().writeValueAsBytes(errorMessage))
                exchange.response.writeWith(Mono.just(responseBody))
            }
            else -> Mono.error(ex)
        }
    }
}
