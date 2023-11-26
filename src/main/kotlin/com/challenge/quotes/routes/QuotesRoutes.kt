package com.challenge.quotes.routes

import com.challenge.quotes.controllers.QuoteController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class QuotesRoutes(private val controller: QuoteController) {

    @Bean
    fun quotesRouter() = router {
        ("/quotes" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("", controller::list)
            GET("/{quoteId}", controller::read)
        }
    }
}
