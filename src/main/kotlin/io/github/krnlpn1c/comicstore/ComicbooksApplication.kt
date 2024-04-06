package io.github.krnlpn1c.comicstore

import io.github.krnlpn1c.comicstore.config.properties.RatingRestClientProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RatingRestClientProperties::class)
class ComicbooksApplication

fun main(args: Array<String>) {
    runApplication<ComicbooksApplication>(*args)
}
