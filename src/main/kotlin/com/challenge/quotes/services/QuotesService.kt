package com.challenge.quotes.services

import com.challenge.quotes.exceptions.ErrorType.QUOTE_NOT_FOUND
import com.challenge.quotes.exceptions.ResourceNotFoundException
import com.challenge.quotes.models.Quote
import com.challenge.quotes.repository.QuoteRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class QuotesService(
    private val quoteRepository: QuoteRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    fun getAllQuotes(pageRequest: Pageable, author: String?): Flux<Quote> {
        return if (author != null) {
            quoteRepository.findByQuoteAuthor(author, pageRequest)
        } else {
            quoteRepository.findByIdNotNull(pageRequest)
        }
    }

    fun getQuoteById(id: String): Mono<Quote> {
        return quoteRepository.findById(id)
            .switchIfEmpty(Mono.error(ResourceNotFoundException(QUOTE_NOT_FOUND)))
    }

    fun countQuotes(author: String?): Mono<Long> {
        return if (author != null) {
            quoteRepository.countByQuoteAuthor(author)
        } else {
            reactiveMongoTemplate.count(Query(), Quote::class.java)
        }
    }
}
