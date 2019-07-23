package com.example.flickr.sample.models

import java.io.Serializable

class PhotoResult : Serializable{
     val page: Int =0
     val pages: Int =0
     val perpage: Int = 0
     val total: String = ""
     val photo: MutableList<PhotoItem> = ArrayList<PhotoItem>()

 }

