package com.challenge.quotes.integration.list

import com.challenge.quotes.integration.IntegrationTests
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.http.MediaType

@TestInstance(PER_CLASS)
@DisplayName("When listing quotes")
class ListQuotesTest : IntegrationTests() {
    @Nested
    @DisplayName("and no pageable parameters are send")
    inner class QuotesListNoPageable {
        @Test
        fun `it should return the quotes list with the default values for pagination`() {
            webTestClient.get()
                .uri("quotes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(10)
                .jsonPath("$.data[*]._id").value(
                    Matchers.contains(
                        "656353343c625cdf906057f3",
                        "656353343c625cdf906057f4",
                        "656353343c625cdf906057f5",
                        "656353343c625cdf906057f6",
                        "656353343c625cdf906057f7",
                        "656353343c625cdf906057f8",
                        "656353343c625cdf906057f9",
                        "656353343c625cdf906057fa",
                        "656353343c625cdf906057fb",
                        "656353343c625cdf906057fc"
                    )
                )
                .jsonPath("$.data[*].quoteAuthor")
                .value(
                    Matchers.contains(
                        "William Shakespeare",
                        "Victor Hugo",
                        "Lauren Bacall",
                        "Voltaire",
                        "Betty Friedan",
                        "Mark Twain",
                        "Pope Paul VI",
                        "Thomas Jefferson",
                        "Francis Bacon",
                        "Albert Camus"
                    )
                )
                .jsonPath("$.data[*].quoteGenre")
                .value(
                    Matchers.contains(
                        "age",
                        "age",
                        "age",
                        "age",
                        "age",
                        "age",
                        "age",
                        "age",
                        "age",
                        "age"
                    )
                )
                .jsonPath("$.totalQuotes").isEqualTo(50000)
                .jsonPath("$.statusCode").isEqualTo(200)
                .jsonPath("$.message").isEqualTo("Quotes")
                .jsonPath("$.pagination.currentPage").isEqualTo(1)
                .jsonPath("$.pagination.nextPage").isEqualTo(2)
                .jsonPath("$.pagination.totalPages").isEqualTo(5000)
        }
    }

    @Nested
    @DisplayName("and pageable parameters were sent")
    inner class QuoteListPageable {
        @Test
        fun `it should return the quotes list and default pageable properties`() {
            webTestClient.get()
                .uri("quotes?page=1&limit=3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(3)
                .jsonPath("$.data[*]._id").value(
                    Matchers.contains(
                        "656353343c625cdf906057f3",
                        "656353343c625cdf906057f4",
                        "656353343c625cdf906057f5"
                    )
                )
                .jsonPath("$.data[*].quoteAuthor")
                .value(
                    Matchers.contains(
                        "William Shakespeare",
                        "Victor Hugo",
                        "Lauren Bacall"
                    )
                )
                .jsonPath("$.data[*].quoteGenre")
                .value(
                    Matchers.contains(
                        "age",
                        "age",
                        "age"
                    )
                )
                .jsonPath("$.totalQuotes").isEqualTo(50000)
                .jsonPath("$.statusCode").isEqualTo(200)
                .jsonPath("$.message").isEqualTo("Quotes")
                .jsonPath("$.pagination.currentPage").isEqualTo(1)
                .jsonPath("$.pagination.nextPage").isEqualTo(2)
                .jsonPath("$.pagination.totalPages").isEqualTo(16667)
        }
    }

    @Nested
    @DisplayName("and pageable and author parameters were sent")
    inner class QuoteListAuthorAndPageable {
        @Test
        fun `it should return the quotes list and default pageable properties`() {
            webTestClient.get()
                .uri("quotes?author=William Shakespeare&page=2&limit=5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(5)
                .jsonPath("$.data[*]._id").value(
                    Matchers.contains(
                        "656353343c625cdf90608236",
                        "656353343c625cdf906089b7",
                        "656353343c625cdf90608a09",
                        "656353343c625cdf90608b5a",
                        "656353343c625cdf90608b5c"
                    )
                )
                .jsonPath("$.data[*].quoteAuthor")
                .value(
                    Matchers.contains(
                        "William Shakespeare",
                        "William Shakespeare",
                        "William Shakespeare",
                        "William Shakespeare",
                        "William Shakespeare"
                    )
                )
                .jsonPath("$.data[*].quoteGenre")
                .value(
                    Matchers.contains(
                        "courage",
                        "death",
                        "death",
                        "death",
                        "death"
                    )
                )
                .jsonPath("$.totalQuotes").isEqualTo(85)
                .jsonPath("$.statusCode").isEqualTo(200)
                .jsonPath("$.message").isEqualTo("Quotes")
                .jsonPath("$.pagination.currentPage").isEqualTo(2)
                .jsonPath("$.pagination.nextPage").isEqualTo(3)
                .jsonPath("$.pagination.totalPages").isEqualTo(17)
        }
    }

    @Nested
    @DisplayName("and the author parameter was sent but nothing was found")
    inner class QuoteListInvalidAuthor {
        @Test
        fun `it should return the quote list and default pageable properties`() {
            webTestClient.get()
                .uri("quotes?author=NotExistingAuthor")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(0)
                .jsonPath("$.totalQuotes").isEqualTo(0)
                .jsonPath("$.statusCode").isEqualTo(200)
                .jsonPath("$.message").isEqualTo("Quotes")
                .jsonPath("$.pagination.currentPage").isEqualTo(1)
                .jsonPath("$.pagination.totalPages").isEqualTo(0)
                .jsonPath("$.pagination.nextPage").isEmpty
        }
    }
}
