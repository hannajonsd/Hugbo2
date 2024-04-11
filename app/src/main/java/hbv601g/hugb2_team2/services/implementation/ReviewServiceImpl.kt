package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.entities.Review
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.services.ReviewService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewServiceImpl() : ReviewService {
    private var networkingService = NetworkingServiceProvider.getNetworkingService()
    private var baseUrl = "/reviews"

    override suspend fun createReview(review: Review): Review? {
        val reqURL = "$baseUrl/create"
        return try {
            val reviewJson = Gson().toJson(review)
            val response = networkingService.postRequest(reqURL, reviewJson)
            Log.d("reviewJsonServiceImpl", "Response: $response")
            // convert response to establishment
            Gson().fromJson(response.toString(), Review::class.java)
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")
            null
        }
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
        return withContext(Dispatchers.IO) {
            try {
                val reviewUrl = "$baseUrl/byEstablishment?establishmentId=${establishment.id}"
                val jsonResponse = networkingService.getRequest(reviewUrl)
                Log.d("getReviews", "Response: $jsonResponse")
                val gson = Gson()
                val listType = object : TypeToken<List<Review>>() {}.type
                gson.fromJson<List<Review>>(jsonResponse, listType)
            } catch (e: Exception) {
                Log.e("getReviews", "Error fetching review for establishment ${establishment.name}", e)
                emptyList<Review>()
            }
        }
    }


    override suspend fun getReviewByUserAndEstablishment(
        user: User,
        establishment: Establishment
    ): Review {
        TODO("Not yet implemented")
    }
}