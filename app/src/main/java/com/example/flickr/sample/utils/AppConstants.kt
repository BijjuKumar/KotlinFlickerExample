package com.example.flickr.sample.utils

object AppConstants {
    const val BASE_URL = "https://api.flickr.com"

    const val PREF_NAME = "app_pref"

    const val NETWORK_TIMEOUT_SEC: Long = 40

    const val ARGS_QUERY: String = "args_query"


    const val DEFAULT_QUERY = "humans"
    const val API_KEY = "dc2242530334eff5c97106c9110de945"

    const val METHOD_SEARCH = "flickr.photos.search"
    const val FORMAT = "json"
    const val NOCALLBACK : Int = 1
    const val SAFESEARCH : Int = 1
    const val SMALL_SQUARE = "s"


    fun getFlickrImageLink(id: String, secret: String, server: String, farm: Int, size: String): String {
        return "https://farm$farm.staticflickr.com/$server/${id}_${secret}_$size.jpg"
    }

}