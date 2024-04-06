package io.github.krnlpn1c.comicstore.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.krnlpn1c.comicstore.client.RatingRestClient
import io.github.krnlpn1c.comicstore.config.properties.RatingRestClientProperties
import io.netty.handler.logging.LogLevel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat

@Configuration
class RatingRestClientConfiguration(
    val properties: RatingRestClientProperties
) {
    @Bean
    fun ratingRestClient(builder: WebClient.Builder, objectMapper: ObjectMapper): RatingRestClient {
        val webClient = builder
            .exchangeStrategies(exchangeStrategies(objectMapper))
            .baseUrl(properties.baseUrl)
            .build()

        return HttpServiceProxyFactory.builder()
            .exchangeAdapter(WebClientAdapter.create(webClient))
            .build()
            .createClient(RatingRestClient::class.java)
    }

    private fun exchangeStrategies(objectMapper: ObjectMapper): ExchangeStrategies {
        return ExchangeStrategies
            .builder()
            .codecs { configurer: ClientCodecConfigurer ->
                configurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
                configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            }.build()
    }
}
