package io.github.krnlpn1c.comicstore.repository

import io.github.krnlpn1c.comicstore.domain.BOOKS_QUERY
import io.github.krnlpn1c.comicstore.domain.Book
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

class BookPagingRepositoryImpl: BookPagingRepository {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun booksPageQuery(name: String?, author: String?, character: String?, size: Int, page: Int): MutableList<Book> {
        val query = entityManager.createNamedQuery(BOOKS_QUERY, Book::class.java)
        query.setParameter("bookName", name?.let { "%${it.lowercase()}%" })
        query.setParameter("authorName", author?.let { "%${it.lowercase()}%" })
        query.setParameter("characterName", character?.let { "%${it.lowercase()}%" })
        query.firstResult = size * page
        query.maxResults = size

        return query.resultList
    }
}
