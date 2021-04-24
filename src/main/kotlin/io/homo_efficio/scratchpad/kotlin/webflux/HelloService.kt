package io.homo_efficio.scratchpad.kotlin.webflux

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class HelloService(
        private val repo: HelloRepository
    ) {

    suspend fun save(message: HelloMessage): HelloMessage {
        val (id, username, msg) = message
        val dbHello = repo.save(Hello(id, username, msg)).awaitSingle()
        return HelloMessage(dbHello.id, dbHello.username, dbHello.msg)
    }
}
