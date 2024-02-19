package io.github.krnlpn1c.comicstore.dto.response

import io.github.krnlpn1c.comicstore.dto.BookDto

data class BooksResponse (
    val books: List<BookDto>,
    val meta: MetaData
)
