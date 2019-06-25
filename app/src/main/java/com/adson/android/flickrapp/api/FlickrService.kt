package com.adson.android.flickrapp.api

import android.net.Uri
import com.adson.android.flickrapp.models.FlickrPhoto
import com.adson.android.flickrapp.models.FlickrSearchResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    fun search(
        @Query("text") input: String,
        @Query("per_page") number: Int = 25,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey : String = getFlickApiKey()): Call<FlickrSearchResponse>

    companion object {
        private const val BASE_URL = "https://api.flickr.com"

        fun create(): FlickrService {
            val client = OkHttpClient.Builder()
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrService::class.java)
        }
    }
}

/**
 * Search photos based on a query.
 * @param query keyword
 * @param perPage number of items per page
 * @param page request page index
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of photos received
 * @param onError function that defines how to handle errors
 */
fun searchFlickr(
    service: FlickrService,
    query: String,
    perPage: Int,
    page: Int,
    onSuccess: (photos: List<FlickrPhoto>) -> Unit,
    onError: (error: String) -> Unit
) {
    service.search(query, perPage, page).enqueue(
        object : Callback<FlickrSearchResponse> {
            override fun onFailure(call: Call<FlickrSearchResponse>?, t: Throwable) {
                onError(t.message ?: "unknown error")
            }

            override fun onResponse(
                call: Call<FlickrSearchResponse>?,
                response: Response<FlickrSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val photos = response.body()?.photos?.photo ?: emptyList()
                    if(!photos.isEmpty()) {
                        for(item in photos) {
                            item.querySearchStr = query //Associate photos to a specific queryTerms
                        }
                    }
                    onSuccess(photos)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        }
    )
}


private fun getFlickApiKey() : String {
    return "1508443e49213ff84d566777dc211f2a"
}

/**
 * Helper method to build the Flick photo URL base.
 */
fun getPhotoUrl(farm: Int, server: String, id: String, secret: String): Uri {
    return Uri.parse(String.format("http://farm%d.static.flickr.com/%s/%s_%s.jpg", farm, server, id, secret))
}