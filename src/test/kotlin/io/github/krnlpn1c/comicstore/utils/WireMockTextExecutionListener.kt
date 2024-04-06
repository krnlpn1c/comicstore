package io.github.krnlpn1c.comicstore.utils

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener

class WireMockTextExecutionListener: AbstractTestExecutionListener() {

    override fun afterTestExecution(testContext: TestContext) {
        resetWireMock(testContext)
    }

    private fun resetWireMock(testContext: TestContext) {
        testContext.applicationContext.getBean(WireMockServer::class.java)
            .resetAll()
    }
}