package pl.allegro.agh.cache.app.adapter.userservice

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.data.redis.core.RedisOperations
import pl.allegro.agh.cache.app.domain.UserScoreProvider
import java.time.Duration

class UserServiceClientWithRedisCache(
    private val userServiceClient: UserServiceClient,
    private val redisOperations: RedisOperations<String, Int>,
    meterRegistry: MeterRegistry,
) : UserScoreProvider {

    private val hitCounter = meterRegistry.counter("redis_hit_count")
    private val missCounter = meterRegistry.counter("redis_miss_count")

    init {
        meterRegistry.gauge("redis_hit_rate", hitCounter) {
            val hits = hitCounter.count()
            val misses = missCounter.count()

            hits / (hits + misses)
        }
    }

    override fun getUserScore(userId: String): Int {
        val key = "user-cache:$userId"
        val cachedUserScore = redisOperations.opsForValue().get(key)
        if (cachedUserScore != null) {
            hitCounter.increment()
            return cachedUserScore
        } else {
            missCounter.increment()
            val userScore = userServiceClient.getUserScore(userId)
            redisOperations.opsForValue().set(key, userScore, Duration.ofSeconds(30))
            return userScore
        }
    }

}
