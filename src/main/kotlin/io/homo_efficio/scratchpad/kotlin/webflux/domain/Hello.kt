package io.homo_efficio.scratchpad.kotlin.webflux.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Hello(
    @Id
    val id: String?,
    val username: String,
    val msg: String?
)
