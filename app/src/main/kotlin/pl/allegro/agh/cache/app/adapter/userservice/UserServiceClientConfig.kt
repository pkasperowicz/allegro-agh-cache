package pl.allegro.agh.cache.app.adapter.userservice

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.RedisOperations
import org.springframework.web.client.RestTemplate
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
    @Profile("nocache")
    fun userScoreProvider(
        restTemplate: RestTemplate,
        clientConfig: ClientConfig,
        meterRegistry: MeterRegistry,
    ): UserScoreProvider {
        return UserServiceClient(restTemplate, clientConfig.url)
    }


    @Bean
    @Profile("localCache")
    fun userScoreProviderWithLocalCache(
        restTemplate: RestTemplate,
        clientConfig: ClientConfig,
        meterRegistry: MeterRegistry,
    ): UserScoreProvider {
        val userServiceClient = UserServiceClient(restTemplate, clientConfig.url)
        return UserServiceClientWithCaffeineCache(userServiceClient, meterRegistry)
    }

    @Bean
    @Profile("redis")
    fun userScoreProviderWithRedisCache(
        restTemplate: RestTemplate,
        clientConfig: ClientConfig,
        meterRegistry: MeterRegistry,
        redisOperations: RedisOperations<String, Int>
    ): UserScoreProvider {
        val userServiceClient = UserServiceClient(restTemplate, clientConfig.url)
        return UserServiceClientWithRedisCache(userServiceClient, redisOperations, meterRegistry)
    }

}

@ConfigurationProperties("user-service")
@ConstructorBinding
data class ClientConfig(
    val url: String,
    val connectionTimeout: Duration,
    val readTimeout: Duration,
)
