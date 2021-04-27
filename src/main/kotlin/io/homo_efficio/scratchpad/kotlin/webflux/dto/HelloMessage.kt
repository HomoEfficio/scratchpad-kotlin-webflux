package io.homo_efficio.scratchpad.kotlin.webflux.dto

import io.homo_efficio.scratchpad.kotlin.webflux.domain.Hello
import javax.validation.constraints.Size

data class HelloMessage(
    val id: String?,

    @field: Size(max = 3)
    val username: String,

    val msg: String?
) {
    companion object {
        fun fromEntity(e: Hello): HelloMessage {
            return HelloMessage(e.id, e.username, e.msg)
        }
    }
}

