package com.black.redisui.service

import com.black.redisui.model.RedisKey
import com.black.redisui.model.KeysResponse
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReactiveRedisService(private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>) {

  fun getValue(key: String): Mono<RedisKey> {
    return reactiveRedisTemplate.opsForValue()[key]
      .flatMap { value ->
        println("Found value: $value")
        getKeyTTL(key)
          .map { ttl -> RedisKey(key, value, ttl) }
      }
  }

  fun setValue(key: String, value: String): Mono<Boolean> {
    return reactiveRedisTemplate.opsForValue().set(key, value)
  }

  fun deleteKey(key: String): Mono<Boolean> {
    return reactiveRedisTemplate.delete(key).hasElement()
  }

  fun getAllKeys(): Mono<List<String>> {
    return reactiveRedisTemplate.keys("*")
      .map { key ->
        println("Found key: $key")
        key
      }
      .collectList()
  }

 fun getKeyTTL(key: String): Mono<Long> {
    return reactiveRedisTemplate.getExpire(key)
      .map { ttl ->
        val minutes = ttl.toMinutes()
        if (minutes < 0) {
          -1 // -1 means the key doesn't have an expiration
        }
        else minutes
      }
  }
}
