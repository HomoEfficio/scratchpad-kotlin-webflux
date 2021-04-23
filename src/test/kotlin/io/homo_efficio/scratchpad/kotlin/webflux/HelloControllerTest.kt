package io.homo_efficio.scratchpad.kotlin.webflux

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters

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
            .consumeWith {
                log.debug("response: {}", it)
                assertThat(it.responseBody).isEqualTo("omwomw")
            }
    }

    @Test
    fun `new hello message on suspend fun`() {
        wtc.post().uri("/hello")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(
                HelloMessage(null, "Homo Efficio", "Hi all~ I'm omwomw")))
            .exchange()
            .expectBody<HelloMessage>()
            .consumeWith {
                log.debug("msg.name: ${it.responseBody?.name}")
                log.debug("msg.msg: ${it.responseBody?.msg}")
                log.debug("${it.responseBody}")
            }
    }
}
