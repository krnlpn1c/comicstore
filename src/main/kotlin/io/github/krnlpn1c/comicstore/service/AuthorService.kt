package io.github.krnlpn1c.comicstore.service

import io.github.krnlpn1c.comicstore.client.RatingRestClient
import io.github.krnlpn1c.comicstore.domain.Author
import io.github.krnlpn1c.comicstore.domain.Book
import io.github.krnlpn1c.comicstore.dto.AuthorBookInfo
import io.github.krnlpn1c.comicstore.dto.AuthorBooks
import io.github.krnlpn1c.comicstore.dto.AuthorDetailsDto
import io.github.krnlpn1c.comicstore.dto.client.RatingQueryDto
import io.github.krnlpn1c.comicstore.repository.AuthorRepository
import io.github.krnlpn1c.comicstore.repository.BookRepository
import io.github.krnlpn1c.comicstore.utils.calculateAvgRating
import jakarta.persistence.EntityNotFoundException
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val ratingRestClient: RatingRestClient
) {
    fun findAuthors(authors: Set<String>): List<Author> {
        return authorRepository.findAllByNameIn(authors)
    }

    fun authorDetails(authorId: Int): AuthorDetailsDto {
        val author = authorRepository.findByIdOrNull(authorId) ?: throw EntityNotFoundException("Author with id $authorId not found")
        val books = bookRepository.findAllByAuthors_Id(authorId)
        var avgRating: BigDecimal? = null
        if (books.isNotEmpty()) {
            val ratingsResponse = runBlocking { ratingRestClient.queryBooksRating(RatingQueryDto(books.map(Book::id))) }
            avgRating = calculateAvgRating(ratingsResponse.booksRating)
        }
        return AuthorDetailsDto(
            author.id,
            author.name,
            AuthorBooks(
                books.map { AuthorBookInfo(it.id, it.name) },
                avgRating
            )
        )
    }
}