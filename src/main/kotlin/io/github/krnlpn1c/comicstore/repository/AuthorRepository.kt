package io.github.krnlpn1c.comicstore.repository

import io.github.krnlpn1c.comicstore.domain.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository: JpaRepository<Author, Int> {
    fun findAllByNameIn(authors: Set<String>): List<Author>
}
