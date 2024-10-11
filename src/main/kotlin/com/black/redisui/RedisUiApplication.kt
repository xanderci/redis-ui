package com.black.redisui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedisUiApplication

fun main(args: Array<String>) {
  runApplication<RedisUiApplication>(*args)
}
