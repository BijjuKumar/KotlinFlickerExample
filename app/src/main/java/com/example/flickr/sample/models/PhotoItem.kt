package com.example.flickr.sample.models

import java.io.Serializable

class PhotoItem : Serializable{

   /* {"id":"48353842926","owner":"50824080@N04","secret":"110242f36a","server":"65535"
        ,"farm":66,"title":"Life will never succumb to human activity on the planet"
        ,"ispublic":1,"isfriend":0,"isfamily":0}*/



    var id: String = ""
    var owner: String = ""
    var secret: String = ""
    var server: String = ""
    var farm: Int =0
    var title: String = ""
    var ispublic: Short = 0
}