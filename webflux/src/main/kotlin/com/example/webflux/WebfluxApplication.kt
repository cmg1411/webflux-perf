package com.example.webflux

import io.netty.channel.MultithreadEventLoopGroup
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebfluxApplication

fun main(args: Array<String>) {
    val a: MultithreadEventLoopGroup
    runApplication<WebfluxApplication>(*args)
}
