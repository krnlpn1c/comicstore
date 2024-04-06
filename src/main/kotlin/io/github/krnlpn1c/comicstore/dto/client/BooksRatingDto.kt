package io.github.krnlpn1c.comicstore.dto.client

import java.math.BigDecimal

data class BooksRatingDto(
    val booksRating: List<BookRating>
)

data class BookRating(
    val bookId: Int,
    val rating: BigDecimal
)
