package pl.allegro.agh.cache.app.adapter.api

import io.micrometer.core.annotation.Timed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.allegro.agh.cache.app.domain.SpecialOfferService

@RestController
class CartEndpoint(
    private val specialOfferService: SpecialOfferService,
) {

    @Timed("special_offer", percentiles = [0.95, 0.99, 0.9])
    @GetMapping("/cart/special-offer/{userId}")
    fun getSpecialOffer(@PathVariable userId: String): ResponseEntity<SpecialOfferResponse> {
        return specialOfferService.getSpecialOfferForUser(userId)
            .let {
                ResponseEntity.ok(SpecialOfferResponse(it.specialOfferAvailable, it.discount))
            }
    }

}

data class SpecialOfferResponse(val specialOfferAvailable: Boolean, val discountPercentage: Int)
