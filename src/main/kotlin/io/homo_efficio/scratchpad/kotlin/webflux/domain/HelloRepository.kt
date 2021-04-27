package io.homo_efficio.scratchpad.kotlin.webflux.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface HelloRepository : ReactiveMongoRepository<Hello, String>
