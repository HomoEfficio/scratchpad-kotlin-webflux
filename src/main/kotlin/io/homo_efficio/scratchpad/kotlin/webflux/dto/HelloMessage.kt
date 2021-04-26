package io.homo_efficio.scratchpad.kotlin.webflux.dto

import io.homo_efficio.scratchpad.kotlin.webflux.ZeroArgConstructor
import javax.validation.constraints.Size

@ZeroArgConstructor
data class HelloMessage(
    val id: String?,

    @field: Size(max = 3)
    val username: String,

    val msg: String?
)
