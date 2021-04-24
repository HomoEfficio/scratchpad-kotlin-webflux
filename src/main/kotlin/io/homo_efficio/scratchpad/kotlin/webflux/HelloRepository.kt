package io.homo_efficio.scratchpad.kotlin.webflux

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface HelloRepository : ReactiveMongoRepository<Hello, String>
