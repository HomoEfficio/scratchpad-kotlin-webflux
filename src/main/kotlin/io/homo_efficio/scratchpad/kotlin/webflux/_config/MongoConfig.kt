package io.homo_efficio.scratchpad.kotlin.webflux._config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.transaction.reactive.TransactionalOperator

@Configuration
class MongoConfig : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName(): String {
        return "my_reactive"
    }

    @Bean
    fun transactionManager(reactiveMongoDatabaseFactory: ReactiveMongoDatabaseFactory): ReactiveMongoTransactionManager {
        return ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory)
    }

    @Bean
    fun transactionalOperator(reactiveMongoTransactionManager: ReactiveMongoTransactionManager): TransactionalOperator {
        return TransactionalOperator.create(reactiveMongoTransactionManager)
    }
}
