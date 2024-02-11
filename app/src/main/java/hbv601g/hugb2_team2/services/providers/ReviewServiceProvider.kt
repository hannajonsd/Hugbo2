package hbv601g.hugb2_team2.services.providers

import hbv601g.hugb2_team2.services.ReviewService
import hbv601g.hugb2_team2.services.implementation.ReviewServiceImpl

object ReviewServiceProvider {

    private var reviewService: ReviewService? = null

    fun getReviewService(): ReviewService {
        if (reviewService == null) {
            reviewService = ReviewServiceImpl()
        }
        return reviewService!!
    }
}