package pl.allegro.agh.cache.app.adapter.userservice

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import pl.allegro.agh.cache.app.domain.UserScoreProvider
import java.time.Duration

class CachedUserServiceClient(private val userServiceClient: UserServiceClient): UserScoreProvider {

    private val cache: LoadingCache<String, Int> = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(Duration.ofSeconds(60))
        .recordStats()
        .build {
            userServiceClient.getUserScore(it)
        }

    override fun getUserScore(userId: String): Int {
        return cache[userId]!!
    }

}
