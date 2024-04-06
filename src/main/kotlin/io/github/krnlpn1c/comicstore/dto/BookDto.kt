package io.github.krnlpn1c.comicstore.dto

import java.math.BigDecimal
import java.time.OffsetDateTime

data class BookDto(
    val id: Int,
    val name: String,
    var publisher: String,
    val releaseDt: OffsetDateTime,
    val authors: List<AuthorDto>,
    val characters: List<CharaDto>,
    val rating: BigDecimal?
)
