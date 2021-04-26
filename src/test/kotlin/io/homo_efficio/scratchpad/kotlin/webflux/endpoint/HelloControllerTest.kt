package io.homo_efficio.scratchpad.kotlin.webflux.endpoint

import io.homo_efficio.scratchpad.kotlin.webflux.dto.HelloMessage
import io.homo_efficio.scratchpad.kotlin.webflux.service.HelloService
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(HelloController::class)
internal class HelloControllerTest {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired private lateinit var wtc: WebTestClient

    @MockBean
    private lateinit var svc: HelloService


    @Test
    fun `simple greeting`() {
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
    fun `new invalid hello message on suspend fun`() {
        val username = "Homo"
        stubNewMessage(username)

        responseSpecNewMessage(username)
            .expectStatus().isBadRequest
    }

    @Test
    fun `new hello message on suspend fun`() {
        val username = "Hom" +
                ""
        stubNewMessage(username)

        responseSpecNewMessage(username)
            .expectStatus().isOk
            .expectBody<HelloMessage>()
            .consumeWith {
                val helloMessage = it.responseBody
                log.debug("msg.username: ${helloMessage?.username}")
                log.debug("msg.msg: ${helloMessage?.msg}")
                log.debug("$helloMessage")
                assertThat(helloMessage?.username).isEqualTo(username)
                assertThat(helloMessage?.msg).isEqualTo("Hi all~ I am Homo Efficio")
            }
    }


    private fun stubNewMessage(username: String) {
        runBlocking {
            given(svc.save(any()))
                .willReturn(
                    HelloMessage(
                        "1",
                        username,
                        "Hi all~ I am Homo Efficio"
                    )
                )
        }
    }

    private fun responseSpecNewMessage(username: String) = wtc.post().uri("/hello")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(
            HelloMessage(
                null,
                username,
                "Hi all~ I am Homo Efficio"
            )
        )
        .exchange()

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}
