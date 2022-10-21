package pl.allegro.agh.cache.app.domain

import org.springframework.stereotype.Service

@Service
class SpecialOfferService(val scoreProvider: UserScoreProvider) {
    fun getSpecialOfferForUser(userId: String): SpecialOfferDto {
        val score = scoreProvider.getUserScore(userId)
        return when {
            score > 90 -> SpecialOfferDto(userId = userId, specialOfferAvailable = true, discount = 80)
            score > 80 -> SpecialOfferDto(userId = userId, specialOfferAvailable = true, discount = 60)
            score > 70 -> SpecialOfferDto(userId = userId, specialOfferAvailable = true, discount = 40)
            score > 60 -> SpecialOfferDto(userId = userId, specialOfferAvailable = true, discount = 20)
            else -> SpecialOfferDto(userId = userId, specialOfferAvailable = false, discount = 0)
        }
    }
}
