package io.github.krnlpn1c.comicstore.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application.client.rating-rest")
data class RatingRestClientProperties(
    val baseUrl: String
)