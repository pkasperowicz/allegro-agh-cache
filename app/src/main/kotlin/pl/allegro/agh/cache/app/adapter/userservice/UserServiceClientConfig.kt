package pl.allegro.agh.cache.app.adapter.userservice

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
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
    fun userServiceClient(restTemplate: RestTemplate, clientConfig: ClientConfig): UserServiceClient {
        return UserServiceClient(restTemplate, clientConfig.url)
    }

}

@ConfigurationProperties("user-service")
@ConstructorBinding
data class ClientConfig(val url: String, val connectionTimeout: Duration, val readTimeout: Duration)
