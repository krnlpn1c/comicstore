package io.github.krnlpn1c.comicstore.repository

import io.github.krnlpn1c.comicstore.domain.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Int>, BookPagingRepository
