package io.homo_efficio.scratchpad.kotlin.webflux

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RequestMapping("/hello")
@RestController
class HelloController {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/greeting/{name}")
    fun greeting(@PathVariable name: String): Mono<ResponseEntity<String>> {
        return Mono.just(ResponseEntity.ok(name))
    }
}
