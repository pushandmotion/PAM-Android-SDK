package com.pushandmotion.sdk

import com.pushandmotion.sdk.models.PAMEvent
import khttp.post

class APIClient(val pamServer: String) {

    var apiURLCache = ""
    //var _urlSession: URLSession?

    fun getEventAPIURL(): String? {
        if( apiURLCache != "" ){
            return apiURLCache
        }
        apiURLCache = if( pamServer.endsWith("/") ){
            "${pamServer}trackers/events"
        } else {
            "${pamServer}/trackers/events"
        }
        if (apiURLCache == "" ){
            return null
        }
        return apiURLCache
    }

    fun sendEvent(event: PAMEvent) {
        val apiURL = getEventAPIURL()
        apiURL?.let{ url ->
            val headers = mutableMapOf<String, String>()
            headers["Content-Type"] = "application/json"
            headers["Accept"] = "application/json"

            val data = event.toDictionary()

            post(url=url, headers=headers, data=data )
        }
    }


}