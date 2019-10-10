package com.pushandmotion.sdk.models

import android.os.Build
import com.pushandmotion.sdk.PAM
import com.pushandmotion.sdk.utils.DeviceInfo


class PAMEvent{
    companion object{
        const val add_payment_info = "add_payment_info"
        const val add_to_cart = "add_to_cart"
        const val add_to_wishlist = "add_to_wishlist"
        const val begin_checkout = "begin_checkout"
        const val checkout_progress = "checkout_progress"
        const val generate_lead = "generate_lead"
        const val login = "login"
        const val purchase = "purchase"
        const val refund = "refund"
        const val remove_from_cart = "remove_from_cart"
        const val search = "search"
        const val select_content = "select_content"
        const val set_checkout_option = "set_checkout_option"
        const val share = "share"
        const val sign_up = "sign_up"
        const val view_item = "view_item"
        const val view_item_list = "view_item_list"
        const val view_promotion = "view_promotion"
        const val view_search_results = "view_search_results"
        const val open_from_url_scheme = "open_from_url_scheme"
    }

    var eventName:String?  = null
    var pageName:String?  = null
    var formData:Form  = Form()
    var customHeader:Map<String,String>? = null
    var pageURL:String? = null
    var pageTitle:String? = null

    fun toDictionary():Map<String,Any>{

        var dictionary = mutableMapOf<String,Any>()

        formData.customData["platform"] = "android"

        formData.customData["device_model"] = DeviceInfo.getDeviceModel()
        formData.customData["os_version"] = DeviceInfo.getAndroidVersion()
        formData.customData["app_name"] = DeviceInfo.getAppID( PAM.getMain().context )

        formData.customData["app_version"] = DeviceInfo.getAppVersion(PAM.getMain().context)

        formData.customData["screen_width"] = DeviceInfo.getScreenWidth(PAM.getMain().context)
        formData.customData["screen_height"] = DeviceInfo.getScreenHeight(PAM.getMain().context)

        formData.customData["_device_id"] = DeviceInfo.getUUID(PAM.getMain().context)


        if ( DeviceInfo.isDebugMode(PAM.getMain().context) ) {
            formData.customData["app_environment"] = "Development"
        }else {
            formData.customData["app_environment"] = "Production"
        }

        eventName?.let{
            dictionary["event"] = it
        }
        pageURL?.let{
            dictionary["page_url"] = it
        }
        pageTitle?.let{
            dictionary["page_title"] = it
        }

        dictionary["form_fields"] = formData.toDictionary()

        return dictionary
    }

}
