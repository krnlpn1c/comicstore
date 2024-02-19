package io.github.krnlpn1c.comicstore.repository

import io.github.krnlpn1c.comicstore.domain.Book

interface BookPagingRepository {
    fun booksPageQuery(name: String?,
                       author: String?,
                       character: String?,
                       size: Int,
                       page: Int): MutableList<Book>
}

