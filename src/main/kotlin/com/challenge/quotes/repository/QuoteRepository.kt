package com.challenge.quotes.repository

import com.challenge.quotes.models.Quote
import com.mongodb.client.result.DeleteResult
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

    fun save(quote: Quote): Mono<Quote> {
        return reactiveMongoTemplate.save(quote)
    }

    fun delete(quote: Quote): Mono<DeleteResult> {
        return reactiveMongoTemplate.remove(quote)
    }

    companion object {
        private const val QUOTE_AUTHOR_FIELD = "quoteAuthor"
        private const val QUOTE_ID_FIELD = "_id"
    }
}
