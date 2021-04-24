package io.homo_efficio.scratchpad.kotlin.webflux

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories
class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName(): String {
        return "my_reactive"
    }
}
