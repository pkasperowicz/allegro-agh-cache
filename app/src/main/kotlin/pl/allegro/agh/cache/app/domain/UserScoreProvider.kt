package pl.allegro.agh.cache.app.domain

interface UserScoreProvider {
    fun getUserScore(userId: String): Int
}
