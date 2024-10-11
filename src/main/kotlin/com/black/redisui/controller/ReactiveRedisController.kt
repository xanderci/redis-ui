package com.black.redisui.controller

import com.black.redisui.model.RedisKey
import com.black.redisui.service.ReactiveRedisService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/redis")
class ReactiveRedisController(private val redisService: ReactiveRedisService) {

  @GetMapping("/keys")
  fun getKeys(): Mono<List<String>> {
    return redisService.getAllKeys()
  }

  @GetMapping("/get")
  fun getValue(@RequestParam key: String): Mono<ResponseEntity<RedisKey>> {
    return redisService.getValue(key)
      .map { value -> ResponseEntity.ok(value) }
      .defaultIfEmpty(ResponseEntity.notFound().build())
  }

  @PostMapping("/set")
  fun setValue(@RequestParam key: String, @RequestParam value: String): Mono<ResponseEntity<Unit>> {
    return redisService.setValue(key, value)
      .map { ResponseEntity.ok().build() }
  }

  @DeleteMapping("/delete")
  fun deleteKey(@RequestParam key: String): Mono<ResponseEntity<Unit>> {
    return redisService.deleteKey(key)
      .map { ResponseEntity.ok().build() }
  }
}
