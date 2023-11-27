package com.challenge.quotes.integration.read

import com.challenge.quotes.integration.IntegrationTests
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("When making a get by quote id")
class ReadQuotesTest : IntegrationTests() {
    @Nested
    @DisplayName("and the quote id exists")
    inner class QuoteIdExists {
        @Test
        fun `it should return the found quotes`() {
            webTestClient.get()
                .uri("quotes/656353343c625cdf90608b5c")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("_id").isEqualTo("656353343c625cdf90608b5c")
                .jsonPath("quoteAuthor").isEqualTo("William Shakespeare")
                .jsonPath("quoteGenre").isEqualTo("death")
                .jsonPath("quoteText").isEqualTo("Cowards die many times before their deaths the valiant never taste of death but once.")
        }
    }

    @Nested
    @DisplayName("and the quote id does not exist")
    inner class QuoteIdDoesNotExist {
        @Test
        fun `it should return the found quotes`() {
            webTestClient.get()
                .uri("quotes/notExistingQuoteId")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("message").isEqualTo("Quote Not Found")
        }
    }
}
