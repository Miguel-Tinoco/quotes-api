package com.challenge.quotes.controllers

import com.challenge.quotes.extensions.TupleExtensions.component1
import com.challenge.quotes.extensions.TupleExtensions.component2
import com.challenge.quotes.representer.ListQuoteRepresenter
import com.challenge.quotes.representer.Pagination
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
        val page = pageableService.getPage(request)
        val limit = pageableService.getLimit(request)
        val pageable = PageRequest.of(page - 1, limit)

        val author = request.queryParam(AUTHOR).orElse(null)

        val totalQuotes = quotesService.count(author)
        val quotesList = quotesService.getAll(pageable, author).map { quote ->
            ReadQuoteRepresenter(quote)
        }.collectList()

        return Mono.zip(totalQuotes, quotesList).flatMap { (total, quotesRepresenter) ->
            val totalPages = pageableService.getTotalPages(limit, total)
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
        return quotesService.getById(request.pathVariable(QUOTE_ID)).flatMap { quote ->
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
        const val AUTHOR = "author"
    }
}
