package io.homo_efficio.scratchpad.kotlin.webflux

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(HelloController::class)
internal class HelloControllerTest {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired private lateinit var wtc: WebTestClient

    @Test
    fun `greeting`() {
        wtc.get().uri("/hello/greeting/omwomw")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .consumeWith { log.debug("response: {}", it) }
    }
}
