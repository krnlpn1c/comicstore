package io.github.krnlpn1c.comicstore.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SerializationConfiguration {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().registerModules(JavaTimeModule())
}