package com.example.flickr.sample.models


class ClientRespBean {

    var fault: String? = null
    var photos: Any? = null

    constructor() {

    }

    constructor(status: String,  photos: String) : super() {
        this.fault = status
        this.photos = photos
    }

}
