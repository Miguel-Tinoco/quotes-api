package com.challenge.quotes.unit

import com.challenge.quotes.exceptions.ErrorType
import com.challenge.quotes.exceptions.ResourceNotFoundException
import com.challenge.quotes.models.Quote
import com.challenge.quotes.repository.QuoteRepository
import com.challenge.quotes.services.QuotesService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.HttpStatus
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@TestInstance(PER_CLASS)
@DisplayName("When using the Quotes Service")
internal class QuotesServiceTest {
    @Mock
    private val quoteRepository = mock<QuoteRepository>()

    @Mock
    private val reactiveMongoTemplate = mock<ReactiveMongoTemplate>()

    @InjectMocks
    private val quotesService = QuotesService(quoteRepository, reactiveMongoTemplate)

    @Test
    fun `and the getQuoteByID is called with a valid id, it should return the quote`() {
        val quote = Quote(
            "5eb17aaeb69dc744b4e73a08",
            "Who could refrain that had a heart to love and in that heart courage to make love known?",
            "William Shakespeare",
            "courage",
            0
        )
        Mockito.`when`(quoteRepository.findById("5eb17aaeb69dc744b4e73a08")).thenReturn(Mono.just(quote))

        val result = quotesService.getQuoteById("5eb17aaeb69dc744b4e73a08").block()!!

        assertEquals(quote.id, result.id)
        assertEquals(quote.quoteText, result.quoteText)
        assertEquals(quote.quoteAuthor, result.quoteAuthor)
        assertEquals(quote.quoteGenre, result.quoteGenre)
        assertEquals(quote.version, result.version)
    }

    @Test
    fun `and the getQuoteByID is called with an invalid id, it should throw an ResourceNotFoundException`() {
        Mockito.`when`(quoteRepository.findById("NotFoundId")).thenReturn(Mono.empty())

        val exception =
            Assertions.assertThrows(Exception::class.java) {
                quotesService.getQuoteById("NotFoundId").block()
            }

        val causeException = exception.cause as ResourceNotFoundException
        assertEquals(ErrorType.QUOTE_NOT_FOUND.message, causeException.message)
        assertEquals(ErrorType.QUOTE_NOT_FOUND.httpStatus, HttpStatus.NOT_FOUND)
    }

    @Test
    fun `and the getAllQuotes is called with valid arguments but without author query param, it should return the the list of quotes`() {
        val quote1 = Quote(
            "5eb17aaeb69dc744b4e73a08",
            "Who could refrain that had a heart to love and in that heart courage to make love known?",
            "William Shakespeare",
            "courage",
            0
        )
        val quote2 = Quote(
            "5eb17aaeb69dc744b4e73a00",
            "Old age is no place for sissies.",
            "Bette Davis",
            "age",
            0
        )
        val quote3 = Quote(
            "5eb17aaeb69dc744b4e73a09",
            "Who could refrain that had a heart to love and in that heart courage to make love known?",
            "Marcus Tullius Cicero",
            "age",
            0
        )

        val page = 1
        val limit = 3

        val pageable = PageRequest.of(page - 1, limit)
        Mockito.`when`(quoteRepository.findByIdNotNull(pageable))
            .thenReturn(Flux.just(quote1, quote2, quote3))

        val result = quotesService.getAllQuotes(pageable, null).collectList().block()
        assertEquals(3, result!!.count())
    }

    @Test
    fun `and the getAllQuotes is called with valid arguments with author query param, it should return the the list of quotes by author`() {
        val quote1 = Quote(
            "5eb17aaeb69dc744b4e73a08",
            "Who could refrain that had a heart to love and in that heart courage to make love known?",
            "Bette Davis",
            "courage",
            0
        )
        val quote2 = Quote(
            "5eb17aaeb69dc744b4e73a00",
            "Old age is no place for sissies.",
            "Bette Davis",
            "age",
            0
        )
        val quote3 = Quote(
            "5eb17aaeb69dc744b4e73a09",
            "Who could refrain that had a heart to love and in that heart courage to make love known?",
            "Bette Davis",
            "age",
            0
        )

        val page = 1
        val limit = 3
        val author = "Bette Davis"

        val pageable = PageRequest.of(page - 1, limit)
        Mockito.`when`(quoteRepository.findByQuoteAuthor(author, pageable))
            .thenReturn(Flux.just(quote1, quote2, quote3))

        val result = quotesService.getAllQuotes(pageable, author).collectList().block()
        assertEquals(3, result!!.count())
    }
}
