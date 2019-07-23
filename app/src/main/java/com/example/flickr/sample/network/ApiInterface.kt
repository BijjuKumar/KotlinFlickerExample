package com.example.flickr.sample


import com.example.flickr.sample.models.ClientRespBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by aziz on 28/08/18.
 */
interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("/services/rest/")
    fun getSearchedPhotos(@Query("method") method :String,
                    @Query("format") format :String,
                    @Query("api_key") apiKey: String,
                    @Query("text") text: String,
                    @Query("nojsoncallback") noCallback: Int,
                    @Query("safe_search") safeSearch: Int,
                          @Query("page") page: Int): Call<ClientRespBean>



}

