package com.challenge.quotes.repository

import com.challenge.quotes.models.Quote
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface QuoteRepository : ReactiveMongoRepository<Quote, String> {
    fun findByIdNotNull(pageable: Pageable): Flux<Quote>

    fun findByQuoteAuthor(author: String, pageRequest: Pageable): Flux<Quote>

    fun countByQuoteAuthor(author: String): Mono<Long>
}
