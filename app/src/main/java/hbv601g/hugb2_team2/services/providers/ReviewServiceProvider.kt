package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.ReviewService
import hbv601g.hugb2_team2.services.implementation.ReviewServiceImpl

object ReviewServiceProvider {
    private var reviewService: ReviewService? = null

    fun getReviewService(context: Context): ReviewService {
        return reviewService ?: synchronized(this) {
            reviewService ?: createReviewService(context).also { reviewService = it }
        }
    }

    private fun createReviewService(context: Context): ReviewService {
        val service = ReviewServiceImpl(context)
        service.setContext(context)
        return service
    }
}