package io.github.krnlpn1c.comicstore.service

import io.github.krnlpn1c.comicstore.domain.Author
import io.github.krnlpn1c.comicstore.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {
    fun findAuthors(authors: Set<String>): List<Author> {
        return authorRepository.findAllByNameIn(authors)
    }
}