package io.github.krnlpn1c.comicstore.utils

import io.github.krnlpn1c.comicstore.dto.client.BookRating
import java.math.BigDecimal
import java.math.RoundingMode

fun calculateAvgRating(ratings: List<BookRating>): BigDecimal? {
    if (ratings.isEmpty()) {
        return null
    }

    val count = ratings.size
    val ratingSum = ratings.sumOf { it.rating }

    return ratingSum.divide(BigDecimal(count), 2, RoundingMode.HALF_UP)
}
