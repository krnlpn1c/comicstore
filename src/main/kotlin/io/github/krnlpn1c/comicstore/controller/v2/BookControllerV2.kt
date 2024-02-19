package io.github.krnlpn1c.comicstore.controller.v2

import io.github.krnlpn1c.comicstore.dto.BookDto
import io.github.krnlpn1c.comicstore.dto.request.BookCreationRequest
import io.github.krnlpn1c.comicstore.dto.response.BooksResponse
import io.github.krnlpn1c.comicstore.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/books")
class BookControllerV2(
    private val bookService: BookService
) {

    @GetMapping
    fun books(
        @RequestParam name: String?,
        @RequestParam author: String?,
        @RequestParam character: String?,
        @RequestParam size: Int = 20,
        @RequestParam page: Int = 0
    ): BooksResponse {
        return bookService.books(name, author, character, size, page)
    }

    @PostMapping
    fun createBook(
        @RequestBody request: BookCreationRequest
    ): BookDto {
        return bookService.createBook(request)
    }
}
