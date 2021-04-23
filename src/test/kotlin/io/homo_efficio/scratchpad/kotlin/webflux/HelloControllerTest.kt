package io.homo_efficio.scratchpad.kotlin.webflux

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
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
            .bodyValue(
//                HelloMessage(
//                    null,
//                    "Homo Efficio",
//                    "Hi all~ I am Homo Efficio"
//                )
                HelloMsgJava(
                        null,
                "Homo Efficio",
                "Hi all~ I am Homo Efficio"
                )
            )
            .exchange()
            .expectBody<HelloMessage>()
            .consumeWith {
                val helloMessage = it.responseBody
                log.debug("msg.name: ${helloMessage?.username}")
                log.debug("msg.msg: ${helloMessage?.msg}")
                log.debug("$helloMessage")
                assertThat(helloMessage?.username).isEqualTo("Homo Efficio")
                assertThat(helloMessage?.msg).isEqualTo("Hi all~ I am Homo Efficio")
            }
    }
}
