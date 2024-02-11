package hbv601g.hugb2_team2.services

import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.entities.Review
import hbv601g.hugb2_team2.entities.User

interface ReviewService {
    suspend fun createReview(review: Review): Review
    suspend fun editReview(review: Review): Review
    suspend fun deleteReview(review: Review): Boolean
    suspend fun getReviewById(id: Long): Review
    suspend fun getReviewsByEstablishment(establishment: Establishment): List<Review>
    suspend fun getReviewByUserAndEstablishment(user: User, establishment: Establishment): Review

}