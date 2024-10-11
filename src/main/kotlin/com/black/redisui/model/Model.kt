package com.black.redisui.model

data class KeysResponse(val keys: List<RedisKey>)

data class RedisKey(
  val key: String,
  val value: String,
  val ttl: Long
)