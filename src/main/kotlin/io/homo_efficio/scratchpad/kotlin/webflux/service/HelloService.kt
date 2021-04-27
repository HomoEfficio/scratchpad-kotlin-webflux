package io.homo_efficio.scratchpad.kotlin.webflux.service

import io.homo_efficio.scratchpad.kotlin.webflux.domain.Hello
import io.homo_efficio.scratchpad.kotlin.webflux.domain.HelloRepository
import io.homo_efficio.scratchpad.kotlin.webflux.dto.HelloMessage
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import java.util.Collections.unmodifiableList

@Service
class HelloService(
        private val repo: HelloRepository
    ) {

    suspend fun save(message: HelloMessage): HelloMessage {
        val (id, username, msg) = message
        val dbHello = repo.save(Hello(id, username, msg)).awaitSingle()
        return HelloMessage.fromEntity(dbHello)
    }

    suspend fun findAll(): List<HelloMessage> {
        return repo.findAll()
            .map { hello -> HelloMessage.fromEntity(hello) }
            .collectList()
            .map { mutableList -> unmodifiableList(mutableList) as List<HelloMessage> }
            .awaitFirstOrElse { emptyList() }
    }
}
