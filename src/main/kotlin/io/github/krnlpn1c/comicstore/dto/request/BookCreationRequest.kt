package io.github.krnlpn1c.comicstore.dto.request

import java.time.OffsetDateTime

data class BookCreationRequest(
    val name: String,
    var publisher: String,
    val releaseDt: OffsetDateTime,
    val authors: Set<String>,
    val characters: Set<String>
)