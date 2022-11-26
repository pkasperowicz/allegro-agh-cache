package pl.allegro.agh.cache.app.adapter.userservice

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics
import pl.allegro.agh.cache.app.domain.UserScoreProvider
import java.time.Duration

class UserServiceClientWithCaffeineCache(
    private val userServiceClient: UserServiceClient,
    meterRegistry: MeterRegistry,
) : UserScoreProvider {

    private val cache: LoadingCache<String, Int> = Caffeine.newBuilder()
        .maximumSize(50)
        .expireAfterWrite(Duration.ofSeconds(30))
        .recordStats()
        .build {
            userServiceClient.getUserScore(it)
        }

    init {
        CaffeineCacheMetrics.monitor(meterRegistry, cache, "user-score-cache")
    }

    override fun getUserScore(userId: String): Int {
        return cache[userId]!!
    }

}
