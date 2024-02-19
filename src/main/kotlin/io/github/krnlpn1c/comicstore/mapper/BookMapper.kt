package io.github.krnlpn1c.comicstore.mapper

import io.github.krnlpn1c.comicstore.domain.Book
import io.github.krnlpn1c.comicstore.dto.AuthorDto
import io.github.krnlpn1c.comicstore.dto.BookDto
import io.github.krnlpn1c.comicstore.dto.CharaDto

fun Book.toDto() =
    BookDto(
        id = this.id,
        name = this.name,
        publisher = this.publisher,
        releaseDt = releaseDt,
        authors = authors.map { AuthorDto(it.id, it.name) },
        characters = characters.map { CharaDto(it.id, it.name) }
    )
