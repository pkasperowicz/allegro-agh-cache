package pl.allegro.agh.cache.app.app.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CartEndpoint {

    @GetMapping("/cart/special-offer/{userId}")
    fun getSpecialOffer(@PathVariable userId: String): ResponseEntity<String> {
        return ResponseEntity.ok("Hello world $userId")
    }

}
