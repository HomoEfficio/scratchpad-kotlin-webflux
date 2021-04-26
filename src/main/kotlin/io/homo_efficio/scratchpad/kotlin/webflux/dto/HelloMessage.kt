package io.homo_efficio.scratchpad.kotlin.webflux.dto

import javax.validation.constraints.Size

data class HelloMessage(
    val id: String?,

    @field: Size(max = 3)
    val username: String,

    val msg: String?
)
