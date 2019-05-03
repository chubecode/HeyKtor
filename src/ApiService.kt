package chutien.it

import io.reactivex.Single
import response.ForexListResponse
import retrofit2.http.*

interface ApiService {
    @GET("/latest")
    fun getLatest(@Path("access_key") apiKey: String, @Path("base ") base: String = "", @Path("symbols ") target: String = ""): Single<ForexListResponse>
}