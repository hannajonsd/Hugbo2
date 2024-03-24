package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.ReviewService
import hbv601g.hugb2_team2.services.implementation.ReviewServiceImpl

object ReviewServiceProvider {

    private lateinit var reviewService: ReviewService

    fun getReviewService() : ReviewService {
        if (!::reviewService.isInitialized) {
            reviewService = ReviewServiceImpl()
        }
        return reviewService
    }
}