package io.homo_efficio.scratchpad.kotlin.webflux.service

import io.homo_efficio.scratchpad.kotlin.webflux.domain.Hello
import io.homo_efficio.scratchpad.kotlin.webflux.domain.HelloRepository
import io.homo_efficio.scratchpad.kotlin.webflux.dto.HelloMessage
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import java.util.Collections.unmodifiableList

@Service
class HelloService(
        private val repo: HelloRepository
//        , private val txop: TransactionalOperator
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

    suspend fun saveAll(vararg messages: HelloMessage) {
        val size = messages.size
        for (i in 0 until size) {
            if (i == 1) {
                throw RuntimeException("2번째 저장 중 에러")
            }
            val (id, username, msg) = messages[i]
            repo.save(Hello(id, username, msg)).awaitSingle()
        }
    }

    @Transactional
    suspend fun saveAllWithTx(vararg messages: HelloMessage) {
//        txop.executeAndAwait {
            saveAll(*messages)
//        }
    }
}
