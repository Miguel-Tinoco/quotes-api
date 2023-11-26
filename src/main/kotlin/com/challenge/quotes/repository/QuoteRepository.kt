package com.challenge.quotes.repository

import com.challenge.quotes.models.Quote
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface QuoteRepository : ReactiveMongoRepository<Quote, String>
