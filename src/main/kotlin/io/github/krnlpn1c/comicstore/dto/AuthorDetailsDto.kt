package io.github.krnlpn1c.comicstore.dto

import java.math.BigDecimal

data class AuthorDetailsDto (
    val id: Int,
    val name: String,
    val booksInfo: AuthorBooks
)

data class AuthorBooks(
    val books: List<AuthorBookInfo>,
    val avgRating: BigDecimal?
)

data class AuthorBookInfo(
    val id: Int,
    val name: String
)
