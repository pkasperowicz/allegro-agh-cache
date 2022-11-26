package pl.allegro.agh.cache.app.adapter.userservice

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisOperations
import org.springframework.web.client.RestTemplate
import pl.allegro.agh.cache.app.adapter.userservice.ClientConfig.CacheVariants.caffeine
import pl.allegro.agh.cache.app.adapter.userservice.ClientConfig.CacheVariants.none
import pl.allegro.agh.cache.app.adapter.userservice.ClientConfig.CacheVariants.redis
import pl.allegro.agh.cache.app.domain.UserScoreProvider
import java.time.Duration

@Configuration
@EnableConfigurationProperties(ClientConfig::class)
class UserServiceClientConfig {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder, clientConfig: ClientConfig): RestTemplate {
        return builder
            .setConnectTimeout(clientConfig.connectionTimeout)
            .setReadTimeout(clientConfig.readTimeout)
            .build()
    }


    @Bean
    fun userScoreProvider(
        restTemplate: RestTemplate,
        clientConfig: ClientConfig,
        meterRegistry: MeterRegistry,
        redisOperations: RedisOperations<String, Int>
    ): UserScoreProvider {
        val userServiceClient = UserServiceClient(restTemplate, clientConfig.url)
        return when (clientConfig.cache) {
            caffeine -> UserServiceClientWithCaffeineCache(userServiceClient, meterRegistry)
            redis -> UserServiceClientWithRedisCache(userServiceClient, redisOperations, meterRegistry)
            none -> userServiceClient
        }
    }

}

@ConfigurationProperties("user-service")
@ConstructorBinding
data class ClientConfig(
    val url: String,
    val connectionTimeout: Duration,
    val readTimeout: Duration,
    val cache: CacheVariants,
) {
    enum class CacheVariants {
        redis, caffeine, none
    }
}
