package pl.allegro.agh.cache.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.asJavaRandom

@RestController
class UserEndpoint {

    private val random = Random(1234)

    @GetMapping("/user/{userId}")
    fun getSpecialOffer(@PathVariable userId: String): ResponseEntity<UserResponse> {
        // This will simulate service delays
        val delay = abs(200 * random.asJavaRandom().nextGaussian()).toLong() + 100
        Thread.sleep(delay)

        // This is just simulation of changing user score
        return ResponseEntity.ok(UserResponse(userId, random.nextInt(0, 100)))
    }

}

data class UserResponse(val id: String, val score: Int)
