package hbv601g.hugb2_team2.services.network

interface NetworkCallback<T> {

    fun onSuccess(result: T)
    fun onFailure(error: String)

}