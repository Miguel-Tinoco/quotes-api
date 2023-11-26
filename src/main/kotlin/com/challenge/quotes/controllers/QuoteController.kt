package com.challenge.quotes.controllers

import com.challenge.quotes.extensions.TupleExtensions.component1
import com.challenge.quotes.extensions.TupleExtensions.component2
import com.challenge.quotes.representer.ListQuoteRepresenter
import com.challenge.quotes.representer.Pagination
import com.challenge.quotes.representer.QuoteRepresenter
import com.challenge.quotes.representer.ReadQuoteRepresenter
import com.challenge.quotes.services.PageableService
import com.challenge.quotes.services.QuotesService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/quotes")
class QuoteController(
    private val quotesService: QuotesService,
    private val pageableService: PageableService
) {
    fun list(request: ServerRequest): Mono<ServerResponse> {
        val page = pageableService.getFilter(request)
        val pageable = PageRequest.of(page - 1, SIZE)

        val totalQuotes = quotesService.countQuotes()
        val quotesList = quotesService.getAllQuotes(pageable).map { quote ->
            QuoteRepresenter(quote)
        }.collectList()

        return Mono.zip(totalQuotes, quotesList).flatMap { (total, quotesRepresenter) ->
            val totalPages = pageableService.getTotalPages(SIZE, total)
            val nextPage = pageableService.getNextPage(page, totalPages)
            val pagination = Pagination(page, nextPage, totalPages)

            ServerResponse.ok().body(
                fromValue(
                    ListQuoteRepresenter(
                        HTTP_OK,
                        message = QUOTES,
                        pagination,
                        total,
                        quotesRepresenter
                    )
                )
            )
        }
    }

    fun read(request: ServerRequest): Mono<ServerResponse> {
        return quotesService.getQuoteById(request.pathVariable(QUOTE_ID)).flatMap { quote ->
            ServerResponse.ok().body(
                fromValue(
                    ReadQuoteRepresenter(
                        quote
                    )
                )
            )
        }
    }

    companion object {
        const val QUOTE_ID = "quoteId"
        const val QUOTES = "Quotes"
        const val HTTP_OK = "200"
        const val SIZE = 10
    }
}
