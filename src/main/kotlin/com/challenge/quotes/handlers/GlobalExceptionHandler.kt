package com.challenge.quotes.handlers

import com.challenge.quotes.exceptions.ErrorType.UNHANDLED_ERROR
import com.challenge.quotes.exceptions.ResourceNotFoundException
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalExceptionHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    webProperties.resources,
    applicationContext
) {
    init {
        this.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), this::handlerFunction)
    }

    private fun handlerFunction(request: ServerRequest): Mono<ServerResponse> {
        val error = getError(request)

        return when (error) {
            is ResourceNotFoundException -> handleResourceNotFoundException(error)
            else -> handleUnexpectedException()
        }
    }

    private fun handleUnexpectedException(): Mono<ServerResponse> {
        return ServerResponse.status(UNHANDLED_ERROR.httpStatus)
            .bodyValue(mapOf(ERROR_MESSAGE to UNHANDLED_ERROR.message))
    }

    private fun handleResourceNotFoundException(error: ResourceNotFoundException): Mono<ServerResponse> {
        return ServerResponse
            .status(error.errorType.httpStatus)
            .bodyValue(mapOf(ERROR_MESSAGE to error.errorType.message))
    }

    companion object {
        private const val ERROR_MESSAGE = "message"
    }
}
