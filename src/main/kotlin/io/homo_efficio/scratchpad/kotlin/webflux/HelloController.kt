package io.homo_efficio.scratchpad.kotlin.webflux

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RequestMapping("/hello")
@RestController
class HelloController {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/greeting/{name}")
    fun greeting(@PathVariable name: String): Mono<ResponseEntity<String>> {
        return Mono.just(ResponseEntity.ok(name))
    }

//    @PostMapping
//    fun newHello(@Valid @RequestBody msg: HelloMessage):
//            ResponseEntity<HelloMessage> {
//
//        return ResponseEntity.ok(msg)
//    }
    @PostMapping
    fun newHello(@Valid @RequestBody msg: HelloMsgJava):
            ResponseEntity<HelloMsgJava> {

        return ResponseEntity.ok(msg)
    }
}
