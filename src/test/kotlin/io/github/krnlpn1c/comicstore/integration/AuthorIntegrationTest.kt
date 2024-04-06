package io.github.krnlpn1c.comicstore.integration

import com.ninjasquad.springmockk.MockkBean
import io.github.krnlpn1c.comicstore.client.RatingRestClient
import io.github.krnlpn1c.comicstore.domain.Author
import io.github.krnlpn1c.comicstore.domain.Book
import io.github.krnlpn1c.comicstore.repository.AuthorRepository
import io.github.krnlpn1c.comicstore.repository.BookRepository
import io.github.krnlpn1c.comicstore.utils.*
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.time.OffsetDateTime

@IntegrationTest
class AuthorIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var authorRepository: AuthorRepository
    @Autowired
    lateinit var bookRepository: BookRepository
    @MockkBean
    lateinit var ratingRestClient: RatingRestClient

    @Test
    fun `author details without books returns success`() {
        val author = Author("Daniel Keyes Jr").let(authorRepository::save)
        mockMvc.get("/api/v2/authors/${author.id}")
            .andDo { MockMvcResultHandlers.print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.name", equalTo(author.name))
                jsonPath("$.booksInfo.avgRating", nullValue())
            }
    }

    @Test
    fun `author details with books returns success`() {
        var author = Author("Daniel Keyes Jr")
        val book = Book("Flowers for Ultron", "Marvel", OffsetDateTime.now())
            .apply {
                addAuthor(author)
            }.let(bookRepository::save)
        author = book.authors.first()

//        every { ratingRestClient.queryBooksRating() } returns BooksRatingDto(listOf(BookRating(book.id, BigDecimal.TEN)))
        mockMvc.get("/api/v2/authors/${author.id}")
            .andDo { MockMvcResultHandlers.print() }
            .andExpectAll {
                status { isOk() }
                jsonPath("$.name", equalTo(author.name))
                jsonPath("$.booksInfo.avgRating", notNullValue())
            }
    }
}
