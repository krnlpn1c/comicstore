package io.github.krnlpn1c.comicstore.utils

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import org.springframework.test.context.support.TestPropertySourceUtils

class WireMockInitializer: ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val wireMockServer = WireMockServer(WireMockConfiguration().dynamicPort())
        wireMockServer.start()
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            applicationContext, "wiremock.server.port=${wireMockServer.port()}"
        )
        applicationContext.beanFactory.registerSingleton("wireMockServer", wireMockServer)
        applicationContext.addApplicationListener { event: ApplicationEvent? ->
            if (event is ContextClosedEvent) {
                wireMockServer.stop()
            }
        }
    }
}
