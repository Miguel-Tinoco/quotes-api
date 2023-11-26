package com.challenge.quotes.controllers

import com.challenge.quotes.exceptions.GenericException
import com.challenge.quotes.exceptions.ResourceNotFoundException
import com.challenge.quotes.representer.ListQuoteRepresenter
import com.challenge.quotes.representer.QuoteRepresenter
import com.challenge.quotes.services.QuotesService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/quotes")
class QuoteController(
    private val quotesService: QuotesService
) {
    fun list(request: ServerRequest): Mono<ServerResponse> {
        return quotesService.getAllQuotes().map { quote ->
            QuoteRepresenter(quote)
        }.collectList().flatMap { quotesRepresenter ->
            ServerResponse.ok().body(
                fromValue(
                    ListQuoteRepresenter(HTTP_OK, message = QUOTES, quotesRepresenter)
                )
            )
        }
    }

    fun read(request: ServerRequest): Mono<ServerResponse> {
        return quotesService.getQuoteById(request.pathVariable(QUOTE_ID)).flatMap { quote ->
            ServerResponse.ok().body(fromValue(QuoteRepresenter(quote)))
        }
    }

    companion object {
        const val QUOTE_ID = "quoteId"
        const val QUOTES = "Quotes"
        const val HTTP_OK = "200"
    }
}
