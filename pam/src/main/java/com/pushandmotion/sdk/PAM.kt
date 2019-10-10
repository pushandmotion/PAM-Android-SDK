package com.pushandmotion.sdk

import android.content.Context
import com.pushandmotion.sdk.models.PAMEvent
import com.pushandmotion.sdk.utils.DeviceInfo

typealias RegisterPushNotiCallback = (Error?, String?) -> Unit

class PAM {

    private var onRemoteNotificationDidRegistered: RegisterPushNotiCallback? = null

    private var deviceUUID: String?

    private var appId: String
    private var pushNotiKeyMediaAlias: String? = null
    var pamContactID: String? = null
    private val apiClient: APIClient
    var context: Context

    constructor(context: Context, pamServer: String) {
        deviceUUID = DeviceInfo.getUUID(context)
        apiClient = APIClient(pamServer)
        appId = context.packageName
        this.context = context

        _instance = this

        DeviceInfo.getAppUpgradeEvent(context)?.let{
            trackCustomEvent(it)
        }

    }

    companion object {
        private var _instance: PAM? = null

        fun getMain(context: Context? = null, pamServer: String = ""): PAM {
            if (_instance == null) {
                _instance = PAM(context!!, pamServer)
            }
            return _instance!!
        }
    }

    var enableLog: Boolean
        get() = Logger.enableLog
        set(value) {
            Logger.enableLog = value
        }

    fun getDeviceUUID(): String? {
        return deviceUUID
    }

    fun trackCustomEvent(event: PAMEvent) {
        apiClient.sendEvent(event)
    }

    fun trackPageview(
        pageName: String,
        contentId: String? = null,
        pageURL: String? = null,
        contentTitle: String? = null
    ) {

        val event = PAMEvent ()
        event.eventName = "page_view"
        event.formData.customData["page_name"] = pageName

        if (contentTitle != null) {
            event.pageTitle = contentTitle
        } else {
            event.pageTitle = pageName
        }

        contentId?.let {
            event.formData.customData["content_id"] = it
        }

        pageURL?.let {
            event.pageURL = it
        }

        contentTitle?.let {
            event.formData.customData["content_title"] = it
        }

        trackCustomEvent(event)
    }

    fun trackPushToken(pushToken: String?) {
        pushNotiKeyMediaAlias?.let{ alias ->
           pushToken?.let{ token ->
               val event = PAMEvent ()
               event.eventName = "save_push_noti_token"
               event.formData.customData[alias] = token
               trackCustomEvent(event)
           }
        }
    }

    fun trackPushTokenError(error: Error?) {
       error?.let{
           val event = PAMEvent ()
           event.eventName = "push_noti_register_error"
           event.formData.customData["error"] = error.localizedMessage
           trackCustomEvent(event)
       }
    }

    fun trackClick() {
        val event = PAMEvent ()
        trackCustomEvent(event)
    }

    fun setUserLanguage() {
        val event = PAMEvent ()
        trackCustomEvent(event)
    }

    fun setUserTag(tag: String) {
        val event = PAMEvent()
        trackCustomEvent(event)
    }

    fun saveContactID(id: String?) {
        id?.let{
            if(id == "") {return}
            pamContactID = id
            val pref = context.getSharedPreferences("pam", Context.MODE_PRIVATE)
            pref.edit().also {
                it.putString("pam_contact_id", pamContactID)
            }
        }
    }

    fun getContactID() : String? {
        if (pamContactID != null){
            return pamContactID
        }
        val pref = context.getSharedPreferences("pam", Context.MODE_PRIVATE)
        val id = pref.getString("pam_contact_id","")
        if( id == ""){
            return null
        }
        pamContactID = id
        return id
    }

    fun cleanEverything(){
        val pref = context.getSharedPreferences("pam", Context.MODE_PRIVATE)
        pref.edit().clear().commit()
    }

}