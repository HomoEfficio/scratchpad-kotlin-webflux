package io.homo_efficio.scratchpad.kotlin.webflux.service

import io.homo_efficio.scratchpad.kotlin.webflux._config.MongoConfig
import io.homo_efficio.scratchpad.kotlin.webflux.domain.HelloRepository
import io.homo_efficio.scratchpad.kotlin.webflux.dto.HelloMessage
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
@ExtendWith(SpringExtension::class)
@Import(HelloService::class, MongoConfig::class)
internal class HelloServiceTest {

    @Autowired private lateinit var svc: HelloService
    @Autowired private lateinit var repo: HelloRepository

    @BeforeEach
    fun beforeEach() {
        println("Deleting old data")
        repo.deleteAll().block()
    }

    @Test
    fun `find all 2 messages`() {
        runBlocking {  // runBlockingTest 사용 시 This job has not completed yet 발생 -> https://github.com/Kotlin/kotlinx.coroutines/issues/1204
            val msg1 = HelloMessage(
                null,
                "username-01",
                "Message-01"
            )
            val msg2 = HelloMessage(
                null,
                "username-02",
                "Message-02"
            )
            svc.save(msg1)
            svc.save(msg2)

            val actual: List<HelloMessage> = svc.findAll()

            assertThat(actual.size).isEqualTo(2)
        }
    }

    @Test
    fun `not rollback test`() {
        runBlocking {
            val msg1 = HelloMessage(
                null,
                "username-01",
                "Message-01"
            )
            val msg2 = HelloMessage(
                null,
                "username-02",
                "Message-02"
            )


            try {
                svc.saveAll(msg1, msg2)
            } catch (e: Exception) {

            }
            val actual = svc.findAll()
            println("total messages: " + actual.size)
            println("message 1 - id: " + (actual[0].id))
            println("message 1 - username: " + (actual[0].username))
            println("message 1 - msg: " + (actual[0].msg))

            assertThat(actual.size).isEqualTo(1)
        }
    }

    @Test
    fun `rollback test`() {
        runBlocking {
            val msg1 = HelloMessage(
                null,
                "username-01",
                "Message-01"
            )
            val msg2 = HelloMessage(
                null,
                "username-02",
                "Message-02"
            )


            try {
                svc.saveAllWithTx(msg1, msg2)
            } catch (e: Exception) {

            }
            val actual = svc.findAll()
            println("total messages: " + actual.size)

            assertThat(actual.size).isEqualTo(0)
        }
    }
}
