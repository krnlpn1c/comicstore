package io.github.krnlpn1c.comicstore.service

import io.github.krnlpn1c.comicstore.domain.Author
import io.github.krnlpn1c.comicstore.domain.Book
import io.github.krnlpn1c.comicstore.domain.Chara
import io.github.krnlpn1c.comicstore.dto.BookDto
import io.github.krnlpn1c.comicstore.dto.request.BookCreationRequest
import io.github.krnlpn1c.comicstore.dto.response.BooksResponse
import io.github.krnlpn1c.comicstore.dto.response.MetaData
import io.github.krnlpn1c.comicstore.mapper.toDto
import io.github.krnlpn1c.comicstore.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val authorService: AuthorService,
    private val characterService: CharacterService,
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

        return BooksResponse(books.map { it.toDto() }, MetaData(hasNext))
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