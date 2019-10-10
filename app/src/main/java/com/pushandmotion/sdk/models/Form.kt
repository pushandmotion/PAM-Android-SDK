package com.pushandmotion.sdk.models

import com.pushandmotion.sdk.PAM

class Form {
    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var mobile: String? = null
    var transactionCampaignID: String? = null
    var customData = mutableMapOf<String,Any>()
    var contactID: String?  = PAM.getMain().pamContactID

    fun toDictionary() :Map<String,Any> {

        var dict = kotlin.collections.mutableMapOf<String, Any>()

        transactionCampaignID?.let{
            dict["_campaign"] = it
        }
        firstname?.let {
            dict["firstname"] = it
        }
        lastname?.let {
            dict["lastname"] = it
        }
        email?.let {
            dict["email"] = it
        }
        mobile?.let {
            dict["mobile"] = it
        }
        contactID?.let {
            dict["_contact_id"] = it
        }

        customData.forEach {
            dict[it.key] = it.value
        }

        return dict
    }


}