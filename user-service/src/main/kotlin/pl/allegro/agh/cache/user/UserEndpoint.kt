package pl.allegro.agh.cache.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class UserEndpoint {

    private val random = Random(1234)

    @GetMapping("/user/{userId}")
    fun getSpecialOffer(@PathVariable userId: String): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(UserResponse(userId, random.nextInt(0, 100)))
    }

}

data class UserResponse(val id: String, val score: Int)
