package pl.allegro.agh.cache.app.adapter.userservice

import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.allegro.agh.cache.app.domain.UserScoreProvider

class UserServiceClient(
    private val restTemplate: RestTemplate,
    private val baseUrl: String,
): UserScoreProvider {

    override fun getUserScore(userId: String): Int {
        val uri = UriComponentsBuilder.fromUriString(baseUrl)
            .pathSegment("user")
            .pathSegment(userId)
            .build()
            .toUri()
        return restTemplate.getForEntity(uri, UserResponse::class.java).body!!.score
    }
}

private data class UserResponse(val id: String, val score: Int)
