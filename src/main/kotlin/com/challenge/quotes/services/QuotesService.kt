package com.challenge.quotes.services

import com.challenge.quotes.exceptions.ErrorType.QUOTE_NOT_FOUND
import com.challenge.quotes.exceptions.ResourceNotFoundException
import com.challenge.quotes.models.Quote
import com.challenge.quotes.repository.QuoteRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class QuotesService(
    private val quoteRepository: QuoteRepository
) {
    fun getAll(pageRequest: Pageable, author: String? = null): Flux<Quote> {
        return quoteRepository.findAll(pageRequest, author)
    }

    fun getById(id: String): Mono<Quote> {
        return quoteRepository.findById(id)
            .switchIfEmpty(Mono.error(ResourceNotFoundException(QUOTE_NOT_FOUND)))
    }

    fun count(author: String? = null): Mono<Long> {
        return quoteRepository.countAllQuotes(author)
    }
}
