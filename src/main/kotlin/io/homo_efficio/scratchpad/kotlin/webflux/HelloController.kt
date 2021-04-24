package io.homo_efficio.scratchpad.kotlin.webflux

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RequestMapping("/hello")
@RestController
class HelloController(
        private val svc: HelloService
    ) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/greeting/{name}")
    fun greeting(@PathVariable name: String): Mono<ResponseEntity<String>> {
        return Mono.just(ResponseEntity.ok(name))
    }

    @PostMapping
    suspend fun newHello(@Valid @RequestBody msg: HelloMessage):
            ResponseEntity<HelloMessage> {

        return ResponseEntity.ok(svc.save(msg))
    }
}
