package io.github.krnlpn1c.comicstore.service.v1

import io.github.krnlpn1c.comicstore.dto.BookDto
import io.github.krnlpn1c.comicstore.mapper.toDto
import io.github.krnlpn1c.comicstore.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookServiceV1(
    private val bookRepository: BookRepository
) {
    fun booksList(name: String?,
              author: String?,
              character: String?,
              size: Int,
              page: Int): List<BookDto> {
        val books = bookRepository.booksPageQuery(name, author, character, size, page)
        return books.map { it.toDto() }
    }
}
