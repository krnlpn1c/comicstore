package io.github.krnlpn1c.comicstore.controller.v1

import io.github.krnlpn1c.comicstore.dto.BookDto
import io.github.krnlpn1c.comicstore.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class BookControllerV1(
    private val bookService: BookService
) {

    @GetMapping
    fun books(
        @RequestParam name: String?,
        @RequestParam author: String?,
        @RequestParam character: String?,
        @RequestParam size: Int = 20,
        @RequestParam page: Int = 0
    ): List<BookDto> {
        return bookService.booksList(name, author, character, size, page)
    }
}
