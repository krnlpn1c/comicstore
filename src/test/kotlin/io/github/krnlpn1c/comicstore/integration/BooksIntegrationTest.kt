package io.github.krnlpn1c.comicstore.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import io.github.krnlpn1c.comicstore.domain.Author
import io.github.krnlpn1c.comicstore.domain.Book
import io.github.krnlpn1c.comicstore.dto.client.BookRating
import io.github.krnlpn1c.comicstore.dto.client.BooksRatingDto
import io.github.krnlpn1c.comicstore.repository.BookRepository
import io.github.krnlpn1c.comicstore.utils.*
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.math.BigDecimal
import java.time.OffsetDateTime

@IntegrationTest
class BooksIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var wireMockServer: WireMockServer
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun `get books returns success`() {
        val book = createBookWithAuthors()

        val bookRating = BigDecimal("5")
        mockRatingResponse(listOf(BookRating(book.id, bookRating)))

        mockMvc.get("/api/v2/books")
            .andDo { MockMvcResultHandlers.print() }
            .andExpect { status { isOk() } }
            .andExpectAll {
                jsonPath("$.books", hasSize<Array<Any>>(1))
                jsonPath("$.books[0].id", equalTo(book.id))
                jsonPath("$.books[0].rating", equalTo(bookRating.toDouble()))
                jsonPath("$.books[0].authors", hasSize<Array<Any>>(2))
            }
    }

    @Test
    fun `books with unknown author returns empty list`() {
        mockMvc.get("/api/v2/books")
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.books", hasSize<Array<Any>>(0))
            }
    }

    @Test
    fun `create book returns success`() {
        val json = """
            {"name": "The Amazing Spider-Man #2053", "publisher": "Marvel", "releaseDt": "1987-09-15T00:00:00.000000Z", "authors": ["Robert Kirkman"], "characters": ["Spider-Man"]}
        """.trimIndent()
        mockMvc.post("/api/v2/books") {
            content = json
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.name", equalTo("The Amazing Spider-Man #2053"))
            }
    }

    private fun createBookWithAuthors(): Book {
        val authors = listOf(Author("Daniel Keyes Jr"), Author("Robert Oppenheimer"))
        val book = Book("Flowers for Ultron", "Marvel", OffsetDateTime.now())
            .apply {
                authors.forEach { addAuthor(it) }
            }.let(bookRepository::save)

        return book;
    }

    private fun mockRatingResponse(booksRating: List<BookRating>) {
        val response = BooksRatingDto(booksRating)
            .let(objectMapper::writeValueAsString)
        wireMockServer.stubFor(
            WireMock.post(WireMock.urlEqualTo("/api/v1/rating"))
                .willReturn(
                    WireMock.aResponse()
                        .withBody(response)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                )
        )
    }
}
