package com.challenge.quotes.controllers

import com.challenge.quotes.models.Quote
import com.challenge.quotes.repository.QuoteRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/quotes")
class QuoteController (private val quoteRepository: QuoteRepository) {

    @GetMapping("/all")
    fun getAllQuotes() : Flux<Quote> {
        return quoteRepository.findAll();
    }
}
