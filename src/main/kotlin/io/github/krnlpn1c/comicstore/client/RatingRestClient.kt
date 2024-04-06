package io.github.krnlpn1c.comicstore.client

import io.github.krnlpn1c.comicstore.dto.client.BooksRatingDto
import io.github.krnlpn1c.comicstore.dto.client.RatingQueryDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.PostExchange

interface RatingRestClient {
    @PostExchange(url = "/api/v1/rating", contentType = MediaType.APPLICATION_JSON_VALUE, accept = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun queryBooksRating(@RequestBody query: RatingQueryDto): BooksRatingDto
}
