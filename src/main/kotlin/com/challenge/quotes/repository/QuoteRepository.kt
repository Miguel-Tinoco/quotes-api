package com.challenge.quotes.repository

import com.challenge.quotes.models.Quote
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class QuoteRepository(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    fun findAll(pageable: Pageable, quoteAuthor: String? = null): Flux<Quote> {
        if (quoteAuthor != null) {
            val query = Query(Criteria.where(QUOTE_AUTHOR_FIELD).`is`(quoteAuthor)).with(pageable)

            return reactiveMongoTemplate.find(query, Quote::class.java)
        }

        return reactiveMongoTemplate.find(Query().with(pageable), Quote::class.java)
    }

    fun findById(quoteId: String): Mono<Quote> {
        val query = Query(Criteria.where(QUOTE_ID_FIELD).`is`(quoteId))
        return reactiveMongoTemplate.findOne(query, Quote::class.java)
    }

    fun countAllQuotes(quoteAuthor: String? = null): Mono<Long> {
        if (quoteAuthor != null) {
            val query = Query(Criteria.where(QUOTE_AUTHOR_FIELD).`is`(quoteAuthor))
            return reactiveMongoTemplate.count(query, Quote::class.java)
        }

        return reactiveMongoTemplate.count(Query(), Quote::class.java)
    }

    companion object {
        private const val QUOTE_AUTHOR_FIELD = "quoteAuthor"
        private const val QUOTE_ID_FIELD = "_id"
    }
}
