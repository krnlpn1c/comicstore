package io.github.krnlpn1c.comicstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ComicbooksApplication

fun main(args: Array<String>) {
    runApplication<ComicbooksApplication>(*args)
}
