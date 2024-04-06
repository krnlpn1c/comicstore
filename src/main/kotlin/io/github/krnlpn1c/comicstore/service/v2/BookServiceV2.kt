package io.github.krnlpn1c.comicstore.service.v2

import io.github.krnlpn1c.comicstore.client.RatingRestClient
import io.github.krnlpn1c.comicstore.domain.Author
import io.github.krnlpn1c.comicstore.domain.Book
import io.github.krnlpn1c.comicstore.domain.Chara
import io.github.krnlpn1c.comicstore.dto.BookDto
import io.github.krnlpn1c.comicstore.dto.client.RatingQueryDto
import io.github.krnlpn1c.comicstore.dto.request.BookCreationRequest
import io.github.krnlpn1c.comicstore.dto.response.BooksResponse
import io.github.krnlpn1c.comicstore.dto.response.MetaData
import io.github.krnlpn1c.comicstore.mapper.toDto
import io.github.krnlpn1c.comicstore.repository.BookRepository
import io.github.krnlpn1c.comicstore.service.AuthorService
import io.github.krnlpn1c.comicstore.service.CharacterService
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceV2(
    private val authorService: AuthorService,
    private val characterService: CharacterService,
    private val bookRepository: BookRepository,
    private val ratingRestClient: RatingRestClient
) {
    fun books(name: String?,
              author: String?,
              character: String?,
              size: Int,
              page: Int): BooksResponse {

        val books = bookRepository.booksPageQuery(name, author, character, size + 1, page)
        val hasNext = books.size > size
        if (hasNext) {
            books.removeLast()
        }

        val ratingByBookId = if (books.isNotEmpty()) {
            runBlocking { ratingRestClient.queryBooksRating(RatingQueryDto(books.map(Book::id))) }
                .booksRating.associate { it.bookId to it.rating }
        } else {
            emptyMap()
        }

        return BooksResponse(books.map { it.toDto(ratingByBookId[it.id]) }, MetaData(hasNext))
    }

    @Transactional
    fun createBook(request: BookCreationRequest): BookDto {
        val dbAuthors = authorService.findAuthors(request.authors)
        val dbAuthorNames = dbAuthors.map { it.name }.toSet()
        val newAuthors = request.authors.filter { it !in dbAuthorNames }
            .map { Author(it) }

        val dbCharacters = characterService.findCharacters(request.characters)
        val dbCharacterNames = dbCharacters.map { it.name }.toSet()
        val newCharacters = request.characters.filter { it !in dbCharacterNames }
            .map { Chara(it) }

        val book = with(request) {
            Book(name, publisher, releaseDt)
        }
        dbAuthors.forEach { book.addAuthor(it) }
        newAuthors.forEach { book.addAuthor(it) }
        dbCharacters.forEach { book.addCharacter(it) }
        newCharacters.forEach { book.addCharacter(it) }

        return bookRepository.save(book).toDto()
    }
}