package io.github.krnlpn1c.comicstore.integration

import io.github.krnlpn1c.comicstore.repository.BookRepository
import io.github.krnlpn1c.comicstore.service.BookService
import io.github.krnlpn1c.comicstore.utils.IntegrationTest
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@IntegrationTest
class BookIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var bookRepository: BookRepository
    @Autowired
    lateinit var bookService: BookService

    @Test
    fun `books without params returns success`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/books")
        ).andExpect {
            status().isOk()
            jsonPath("$.books", hasSize<Array<Any>>(1))
            jsonPath("$.books[0].authors", hasSize<Array<Any>>(2))
        }
    }

    @Test
    fun `books with unknown author returns empty list`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/books")
                .param("author", "unknown")
        ).andExpect {
            status().isOk()
            jsonPath("$.books", hasSize<Array<Any>>(1))
            jsonPath("$.books[0].authors", hasSize<Array<Any>>(2))
        }
    }

    @Test
    fun `create book returns success`() {
        val json = """
            {"name": "The Amazing Spider-Man #2053", "publisher": "Marvel", "releaseDt": "1987-09-15T00:00:00.000000Z", "authors": ["Robert Kirkman"], "characters": ["Spider-Man"]}
        """.trimIndent()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/books")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect {
            status().isOk()
            jsonPath("$.name", equalTo("The Amazing Spider-Man #2053"))
        }
    }
}
