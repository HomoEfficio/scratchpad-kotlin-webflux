package io.homo_efficio.scratchpad.kotlin.webflux

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
                HelloMessage(
                        null,
                "Homo Efficio",
                "Hi all~ I am Homo Efficio"
                )
            )
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody<String>()
            .consumeWith {
                val mapper = jacksonObjectMapper()
                val responseJson =  """
                    |{\"timestamp\":\"2021-04-24T00:47:17.053+00:00\",
                    |\"path\":\"/hello\",
                    |\"status\":400,
                    |\"error\":\"Bad Request\",
                    |\"message\":\"\",
                    |\"requestId\":\"500d28d7\"}\n
                """.trimIndent()
                val result = mapper.readValue<Map<String, Any>>(responseJson)
                assertThat(result["timestamp"] as String).isNotNull
                assertThat(result["path"] as String).isEqualTo("/hello")
                assertThat(result["status"] as Int).isEqualTo(400)
                assertThat(result["error"] as String).isEqualTo("Bad Request")
                assertThat(result["message"] as String).isEmpty()
                assertThat(result["requestId"] as String).isNotNull()
            }
    }
}
