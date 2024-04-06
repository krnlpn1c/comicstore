package io.github.krnlpn1c.comicstore.unit

import io.github.krnlpn1c.comicstore.dto.client.BookRating
import io.github.krnlpn1c.comicstore.utils.calculateAvgRating
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.stream.Stream

class RatingCalculatorTest {
    @Test
    fun `calculate average of two ratings`() {
        val avg = calculateAvgRating(
            listOf(
                BookRating(bookId = 1, rating = BigDecimal("3")),
                BookRating(bookId = 2, rating = BigDecimal("4")))
        )
        assertEquals(BigDecimal("3.50"), avg)
    }

    @ParameterizedTest
    @MethodSource("ratingCalculatorParameters")
    fun `calculate average rating`(ratings: List<String>, expected: String?) {
        val bookRatings = ratings.mapIndexed { index, ratingString ->
            BookRating(index, BigDecimal(ratingString))
        }
        val avg = calculateAvgRating(bookRatings)
        assertEquals(expected?.let { BigDecimal(it) }, avg)
    }

    companion object {
        @JvmStatic
        fun ratingCalculatorParameters(): Stream<Arguments> =
            Stream.of(
                Arguments.of(listOf("3", "4"), "3.50"),
                Arguments.of(listOf("4"), "4.00"),
                Arguments.of(listOf("3", "4", "3"), "3.33"),
                Arguments.of(emptyList<String>(), null),
            )
    }
}
