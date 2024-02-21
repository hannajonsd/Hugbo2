package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.entities.Review
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.services.ReviewService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class ReviewServiceImpl(context: Context) : ReviewService {
    private lateinit var context: Context
    private var networkingService = NetworkingServiceProvider.getNetworkingService(context)

    override suspend fun createReview(review: Review): Review {
        TODO("Not yet implemented")
    }

    override suspend fun editReview(review: Review): Review {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReview(review: Review): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewById(id: Long): Review {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewsByEstablishment(establishment: Establishment): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewByUserAndEstablishment(
        user: User,
        establishment: Establishment
    ): Review {
        TODO("Not yet implemented")
    }

    override fun setContext(context: Context) {
        this.context = context
    }
}