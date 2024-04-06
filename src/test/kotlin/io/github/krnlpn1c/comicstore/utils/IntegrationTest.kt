package io.github.krnlpn1c.comicstore.utils

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestExecutionListeners.MergeMode
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = [DataSourceInitializer::class, WireMockInitializer::class])
@TestExecutionListeners(listeners = [LiquibaseTextExecutionListener::class, WireMockTextExecutionListener::class], mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@ActiveProfiles("junit")
annotation class IntegrationTest
