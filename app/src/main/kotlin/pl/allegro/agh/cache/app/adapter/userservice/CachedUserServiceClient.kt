package pl.allegro.agh.cache.app.adapter.userservice

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import pl.allegro.agh.cache.app.domain.UserScoreProvider
import java.time.Duration

class CachedUserServiceClient(
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
        Gauge.builder("cache.hit_count") { cache.stats().hitCount() }.register(meterRegistry)
        Gauge.builder("cache.miss_count") { cache.stats().missCount() }.register(meterRegistry)
        Gauge.builder("cache.hit_rate") { cache.stats().hitRate() }.register(meterRegistry)
    }

    override fun getUserScore(userId: String): Int {
        return cache[userId]!!
    }

}
