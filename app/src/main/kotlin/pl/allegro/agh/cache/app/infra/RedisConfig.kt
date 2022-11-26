package pl.allegro.agh.cache.app.infra

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@Import(RedisAutoConfiguration::class)
class RedisConfig {

    @Bean
    fun redisTemplateInt(
        redisConnectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper
    ): RedisTemplate<String, Int> {
        val template = RedisTemplate<String, Int>()
        template.setConnectionFactory(redisConnectionFactory)
//        val serializer = Jackson2JsonRedisSerializer(Resource::class.java)
//        serializer.setObjectMapper(objectMapper)
//        template.hashValueSerializer = serializer
//        template.keySerializer = serializer
        return template
    }

}
