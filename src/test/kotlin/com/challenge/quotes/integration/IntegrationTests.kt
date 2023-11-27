package com.challenge.quotes.integration

import com.challenge.quotes.repository.QuoteRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(PER_CLASS)
abstract class IntegrationTests {
    @LocalServerPort
    private var applicationPort = 0

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var quoteRepository: QuoteRepository

    @BeforeAll
    fun beforeAll() {
        webTestClient =
            WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:$applicationPort")
                .build()
    }
}
